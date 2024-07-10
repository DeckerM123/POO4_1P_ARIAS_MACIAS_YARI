package main.java.com.Proyecto;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import main.java.com.Proyecto.Users.Articulo;
import main.java.com.Proyecto.Users.Autor;
import main.java.com.Proyecto.Users.Revisor;
import main.java.com.Proyecto.manejo.Archivo;
import main.java.com.Proyecto.manejo.EstadoRevision;
import main.java.com.Proyecto.manejo.ManejoDatos;

public abstract class Sistema {
    public static final Scanner sc = new Scanner(System.in);    

    /**
     * Registra un autor y un artículo en el sistema
     * Si el registro es exitoso, se pregunta si se desea enviar el artículo a revisión
     */
    public static void someterArticulo() {
        System.out.println("Datos del Autor");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine().strip();
        System.out.print("Apellido: ");
        String apellido = sc.nextLine().strip();
        System.out.print("Correo: ");
        String correo = sc.nextLine().strip();
        System.out.print("Institución: ");
        String institucion = sc.nextLine().strip();
        System.out.print("Campo de Investigación: ");
        String campoInvestigacion = sc.nextLine().strip();
        Autor autor = new Autor(nombre, apellido, correo, institucion, campoInvestigacion);
        autor.registrarse();

        System.out.println("Datos del Artículo");
        System.out.print("Título: ");
        String titulo = sc.nextLine().strip();
        System.out.print("Resumen: ");
        String resumen = sc.nextLine().strip();
        System.out.print("Contenido: ");
        String contenido = sc.nextLine().strip();
        System.out.print("Palabras clave: (separadas por coma)\n");
        String keywords = sc.nextLine().strip();
        String[] palabrasClave = keywords.split(",");
        Articulo articulo = new Articulo(titulo, resumen, contenido, palabrasClave, autor.getCodigo());
        boolean sometido = autor.someterArticulo(articulo);

        if (sometido) {
            System.out.println("Artículo registrado con éxito");
            System.out.println("¿Desea enviarlo a revisión? S\\N");
            String respuesta = sc.nextLine().strip();
            if (respuesta.equalsIgnoreCase("S")) {
                enviarARevision(autor.getCampoInvestigacion(), articulo);
            } else {
                System.out.println("Artículo guardado con éxito");
            }
        } else {
            System.out.println("Error al registrar artículo");
        }
        Main.menuPrincipal();

    }

    /**
     * Asigna dos revisores al artículo y envía un email a cada uno
     * Se prioriza a los revisores relacionados con la especialidad del autor
     * Si no hay suficientes revisores con la especialidad,
     * se asignan los primeros disponibles
     * @param especialidad Especialidad del autor del artículo
     * @param articulo Artículo a enviar a revisión
     */
    public static void enviarARevision(String especialidad, Articulo articulo) {
        try {
            String[] revisores = ManejoDatos.leerArchivo(Archivo.REVISORES).split("\n");
            Revisor[] listaRevisores = new Revisor[revisores.length];
            for (int i = 0; i < revisores.length; i++) {
                String[] revisor = revisores[i].split(",");
                listaRevisores[i] = new Revisor(revisor[0], revisor[1], revisor[2], revisor[3],
                        revisor[4], Integer.parseInt(revisor[5]));
            }
            Revisor[] revisoresAsignados = new Revisor[2];
            int contadorAsignados = 0;
            for (Revisor revisor : listaRevisores) {
                if (revisor.getEspecialidad().contains(especialidad) && contadorAsignados < 2) {
                    revisoresAsignados[contadorAsignados++] = revisor;
                }
            }
            if (contadorAsignados < 2) {
                for (Revisor revisor : listaRevisores) {
                    if (contadorAsignados < 2 && !Arrays.asList(revisoresAsignados).contains(revisor)) {
                        revisoresAsignados[contadorAsignados++] = revisor;
                    }
                }
            }
            for (Revisor revisor : revisoresAsignados) {
                String registro = EstadoRevision.PENDIENTE + ";" + revisor.getCodigo() + ";" + articulo.getCodigo();
                try {
                    ManejoDatos.escribirArchivo(Archivo.REVISIONES, registro);
                    System.out.println("Revisor asignado con éxito");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error al asignar revisor");
                }
                System.out.println("Enviando email a \"" + revisor.getCorreo() + "\"...");
                String mensaje = "Estimado/a " + revisor.getNombre() + " " + revisor.getApellido() + ",\n\n" +
                        "Ud ha sido seleccionado/a para revisar el artículo titulado \""+ articulo.getTitulo()
                        + "\".\n" +"Le enviamos los datos del presente articulo, se pide lo revise y envíe sus observaciones y consejos lo mas pronto posible.\n\n"
                        +"Resumen: " + articulo.getResumen() + "\n\n" +
                        "Contenido: " + articulo.getContenido() + "\n\n" +
                        "Palabras clave: " + String.join(", ", articulo.getPalabrasClave()) + "\n\n" +
                        "Saludos cordiales,\n" +
                        "Asignación de Revisores";
                boolean enviado = revisor.generarCorreo(revisor.getCorreo(),
                        "Revisión: Artículo #" + articulo.getCodigo(),
                        mensaje);
                if (enviado) {
                    System.out.println("Email enviado con éxito");
                } else {
                    System.out.println("Error al enviar email");
                }

            }
            System.out.println("Artículo enviado a revisión");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al enviar artículo a revisión");
        }
    }

}