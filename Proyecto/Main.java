package main.java.com.revista;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import main.java.com.revista.entidad.Articulo;
import main.java.com.revista.entidad.Editor;
import main.java.com.revista.entidad.Revisor;
import main.java.com.revista.entidad.Usuario;
import main.java.com.revista.util.Archivos;
import main.java.com.revista.util.Autenticacion;
import main.java.com.revista.util.Revisar;
import main.java.com.revista.util.Persistencia;

public class Main {

    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        mostrarMenu();
    }

    /**
     * Muestra el menú principal del sistema
     * El usuario podrá someter un artículo o iniciar sesión
     * Si el usuario inicia sesión como revisor o editor,
     * se mostrará el menú correspondiente
     * Si el usuario no elige ninguna opción, el programa termina
     * Si el usuario elige una opción inválida, el programa termina
     */
    public static void mostrarMenu() {
        System.out.print(
                "1. Someter artículo\n" +
                        "2. Iniciar sesión\n" +
                        "Enter para salir\n" +
                        "Opción: ");
        String opcion = Sistema.sc.nextLine().strip();
        switch (opcion) {
            case "1":
                Sistema.someterArticulo();
                break;
            case "2":
                Usuario usuario = Autenticacion.iniciarSesion();
                if (usuario != null) {
                    if (usuario instanceof Revisor) {
                        mostrarMenu((Revisor) usuario);
                    } else {
                        mostrarMenu((Editor) usuario);
                    }
                } else {
                    mostrarMenu();
                }
                break;
            default:
                Sistema.sc.close();
                System.out.println("Vuelve pronto!");
                System.exit(0);
        }
    }

    /**
     * Muestra el menú de opciones para el revisor que inició sesión
     * El revisor podrá ver los artículos pendientes de revisar y decidir sobre ellos
     * @param revisor Revisor que inició sesión
     */
    public static void mostrarMenu(Revisor revisor) {
        try {
            ArrayList<String> pendientes = Persistencia.busquedaAvanzada(Archivos.REVISIONES,
                    Revisar.PENDIENTE + ";" + revisor.getCodigo());
            if (!pendientes.isEmpty()) {
                System.out.println("Artículos pendientes de revisar:");
                for (String disponible : pendientes) {
                    String[] datos = disponible.split(";");
                    System.out.println("Art: " + datos[2]);
                }

                System.out.println("Ingrese el código del artículo: (enter para cerrar sesión)");
                String codigo = Sistema.sc.nextLine().strip();
                if (!codigo.isBlank()) {
                    String busqueda = Persistencia.busquedaEnArchivo(Archivos.ARTICULOS, codigo);
                    if (busqueda != null) {
                        String[] art = busqueda.split(",");
                        Articulo articulo = new Articulo(art[1], art[2], art[3], art[4].split("#"), art[5]);
                        revisor.decidirSobreArticulo(articulo);
                    } else {
                        System.out.println("Artículo no encontrado");
                    }
                    mostrarMenu(revisor);
                } else {
                    System.out.println("Cerrando sesión...");
                    mostrarMenu();
                }
            } else {
                System.out.println("No hay artículos disponibles para revisar");
                mostrarMenu();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar artículos disponibles");
            mostrarMenu();
        }
    }

    /**
     * Muestra el menú de opciones para el editor que inició sesión
     * El editor podrá ver los artículos pendientes de analizar y decidir sobre ellos
     * @param editor Editor que inició sesión
     */
    public static void mostrarMenu(Editor editor) {
        try {
            ArrayList<String> revisiones = Persistencia.busquedaAvanzada(Archivos.REVISIONES,
                    Revisar.REVISADO);
            if (!revisiones.isEmpty()) {
                System.out.println("Artículos pendientes de revisar:");
                HashSet<String> articulosImpresos = new HashSet<>();
                for (String disponible : revisiones) {
                    String[] datos = disponible.split(";");
                    if (articulosImpresos.add(datos[2])) {
                        System.out.println("Art: " + datos[2]);
                    }
                }

                System.out.println("Ingrese el código del artículo: (enter para cerrar sesión)");
                String codigo = Sistema.sc.nextLine().strip();
                if (!codigo.isBlank()) {
                    ArrayList<String> comentarios = Persistencia.busquedaAvanzada(Archivos.REVISIONES, codigo);
                    for (String comentario : comentarios) {
                        if (comentario.split(";")[0].equals(Revisar.REVISADO)) {
                            System.out.println("- " + comentario.split(";")[3] + " (" + comentario.split(";")[4] + ")");
                        }
                    }
                    String busqueda = Persistencia.busquedaEnArchivo(Archivos.ARTICULOS, codigo);
                    if (busqueda != null) {
                        String[] art = busqueda.split(",");
                        Articulo articulo = new Articulo(art[1], art[2], art[3], art[4].split("#"), art[5]);
                        editor.decidirSobreArticulo(articulo);
                    } else {
                        System.out.println("Artículo no encontrado");
                    }
                    mostrarMenu(editor);
                } else {
                    System.out.println("Cerrando sesión...");
                    mostrarMenu();
                }
            } else {
                System.out.println("No hay artículos disponibles para revisar");
                mostrarMenu();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar artículos disponibles");
            mostrarMenu();
        }
    }

}
