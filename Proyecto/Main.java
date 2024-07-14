package main.java.com.Proyecto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import main.java.com.Proyecto.Users.Articulo;
import main.java.com.Proyecto.Users.Editor;
import main.java.com.Proyecto.Users.Revisor;
import main.java.com.Proyecto.Users.Usuario;
import main.java.com.Proyecto.manejo.Archivo;
import main.java.com.Proyecto.manejo.Verificar;
import main.java.com.Proyecto.manejo.EstadoRevision;
import main.java.com.Proyecto.manejo.ManejoDatos;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        menuPrincipal();
    }

    /**
     * Muestra el menú principal del sistema
     * El usuario podrá someter un artículo o iniciar sesión
     * Si el usuario podra iniciar como revisor o editor
     * Si el usuario no elige ninguna opción, el programa termina
     * Si el usuario elige una opción inválida, el programa termina
     */
    public static void menuPrincipal() {
        System.out.print("\nSi desea salir pulse ENTER\n"+
            "------- Bienvenido -------\n"+
                "1. Someter escrito\n" +
                        "2. Iniciar sesión\n" +
                            "Opción: ");
        String op = Sistema.sc.nextLine().strip();
        if (op.equals("1")) {
            Sistema.someterArticulo();
        } else if (op.equals("2")) {
            Usuario usuario = Verificar.iniciarSesion();
            if (usuario != null) {
                if (usuario instanceof Revisor) {
                    mostrarMenu((Revisor) usuario);
                } else {
                    mostrarMenu((Editor) usuario);
                }
            } else {
                menuPrincipal();
            }
        } else {
            Sistema.sc.close();
            System.out.println("Gracias, vuelva pronto");
            System.exit(0);
        }
    }

    /**
     * Muestra el menú de opciones para el revisor que inició sesión
     * El revisor podrá ver los artículos pendientes de revisar y decidir sobre
     * ellos
     * 
     * @param revisor Revisor que inició sesión
     */
    public static void mostrarMenu(Revisor revisor) {
        try {
            ArrayList<String> pendientes = ManejoDatos.busquedaAvanzada(Archivo.REVISIONES,
                    EstadoRevision.PENDIENTE + ";" + revisor.getCodigo());
            if (!pendientes.isEmpty()) {
                System.out.println("Artículos pendientes de revisar:");
                for (String disponible : pendientes) {
                    String[] datos = disponible.split(";");
                    System.out.println("Art: " + datos[2]);
                }

                System.out.println("Ingrese el código del artículo: ");
                String codigo = Sistema.sc.nextLine().strip();
                if (!codigo.isBlank()) {
                    String busqueda = ManejoDatos.buscarArchivo(Archivo.ARTICULOS, codigo);
                    if (busqueda != null) {
                        String[] art = busqueda.split(",");
                        Articulo articulo = new Articulo(art[1], art[2], art[3], art[4].split("#"), art[5]);
                        revisor.decisionDeArticulo(articulo);
                    } else {
                        System.out.println("Artículo no encontrado");
                    }
                    mostrarMenu(revisor);
                } else {
                    System.out.println("Sesion Cerrada");
                    menuPrincipal();
                }
            } else {
                System.out.println("No hay artículos disponibles");
                menuPrincipal();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar artículos");
            menuPrincipal();
        }
    }

    /**
     * Muestra el menú de opciones para el editor que inició sesión
     * El editor podrá ver los artículos pendientes de analizar y decidir sobre ellos
     * @param editor Editor que inició sesión
     */
    public static void mostrarMenu(Editor editor) {
        try {
            // Obtén la descripción del estado de revisión
            String estadoRevisado = EstadoRevision.REVISADO.getDescripcion();
    
            // Pasa la descripción (cadena) al método busquedaAvanzada
            ArrayList<String> revisiones = ManejoDatos.busquedaAvanzada(Archivo.REVISIONES, estadoRevisado);
    
            if (!revisiones.isEmpty()) {
                System.out.println("Artículos pendientes por revisar:");
                HashSet<String> articulosImpresos = new HashSet<>();
                for (String disponible : revisiones) {
                    String[] datos = disponible.split(";");
                    if (articulosImpresos.add(datos[2])) {
                        System.out.println("Art: " + datos[2]);
                    }
                }
    
                System.out.println("Ingrese el código del artículo:");
                System.out.println("ENTER para cerrar sesión");
                String codigo = Sistema.sc.nextLine().strip();
                if (!codigo.isBlank()) {
                    ArrayList<String> comentarios = ManejoDatos.busquedaAvanzada(Archivo.REVISIONES, codigo);
                    for (String comentario : comentarios) {
                        if (comentario.split(";")[0].equals(EstadoRevision.REVISADO.getDescripcion())) {
                            System.out.println("- " + comentario.split(";")[3] + " (" + comentario.split(";")[4] + ")");
                        }
                    }
                    String busqueda = ManejoDatos.buscarArchivo(Archivo.ARTICULOS, codigo);
                    if (busqueda != null) {
                        String[] art = busqueda.split(",");
                        Articulo articulo = new Articulo(art[1], art[2], art[3], art[4].split("#"), art[5]);
                        editor.decisionDeArticulo(articulo);
                    } else {
                        System.out.println("Artículo no encontrado");
                    }
                    mostrarMenu(editor);
                } else {
                    System.out.println("Sesión cerrada");
                    menuPrincipal(); // Redirigir al menú principal
                }
            } else {
                System.out.println("No hay artículos disponibles para revisar");
                menuPrincipal(); // Redirigir al menú principal
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar artículos disponibles");
            menuPrincipal(); // Redirigir al menú principal
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
