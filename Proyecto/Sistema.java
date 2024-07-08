package main.java.com.revista;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import main.java.com.revista.entidad.Articulo;
import main.java.com.revista.entidad.Autor;
import main.java.com.revista.entidad.Revisor;
import main.java.com.revista.util.Persistencia;
import main.java.com.revista.util.Revisar;
import main.java.com.revista.util.Archivos;

public abstract class Sistema {
    public static final Scanner sc = new Scanner(System.in);    

    /**
     * Registra un autor y un artículo en el sistema
     * Si el registro es exitoso, se pregunta si se desea enviar el artículo a revisión
     */
    public static void someterArticulo() {
        System.out.println("Datos del Autor");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Apellido: ");
        String apellido = sc.nextLine();
        System.out.print("Correo: ");
        String correo = sc.nextLine();
        System.out.print("Institución: ");
        String institucion = sc.nextLine();
        System.out.print("Campo de Investigación: ");
        String campoInvestigacion = sc.nextLine();
        Autor autor = new Autor(nombre, apellido, correo, institucion, campoInvestigacion);
        autor.registrarse();

        System.out.println("Datos del Artículo");
        System.out.print("Titulo: ");
        String titulo = sc.nextLine();
        System.out.print("Resumen: ");
        String resumen = sc.nextLine();
        System.out.print("Contenido: ");
        String contenido = sc.nextLine();
        System.out.print("Palabras clave: (separadas por coma)");
        String keywords = sc.nextLine();
        String[] palabrasClave = keywords.split(",");
        Articulo articulo = new Articulo(titulo, resumen, contenido, palabrasClave, autor.getCodigo());
        boolean sometio = autor.someterArticulo(articulo);

        if (sometio) {
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
        Main.mostrarMenu();

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
            String[] revisores = Persistencia.leerArchivo(Archivos.REVISORES).split("\n");
            Revisor[] listaRevisores = new Revisor[revisores.length];
            for (int i = 0; i < revisores.length; i++) {
                String[] revisor = revisores[i].split(",");
                listaRevisores[i] = new Revisor(revisor[0], revisor[1], revisor[2], revisor[3],
                        revisor[4], Integer.parseInt(revisor[5]));
            }
            Revisor[] revisoresAsignados = new Revisor[2];
            int cont = 0;
            for (Revisor revisor : listaRevisores) {
                if (revisor.getEspecialidad().contains(especialidad) && cont < 2) {
                    revisoresAsignados[cont++] = revisor;
                }
            }
            if (cont < 2) {
                for (Revisor revisor : listaRevisores) {
                    if (cont < 2 && !Arrays.asList(revisoresAsignados).contains(revisor)) {
                        revisoresAsignados[cont++] = revisor;
                    }
                }
            }
            for (Revisor revisor : revisoresAsignados) {
                String registro = Revisar.PENDIENTE + ";" + revisor.getCodigo() + ";" + articulo.getCodigo();
                try {
                    Persistencia.escribirArchivo(Archivos.REVISIONES, registro);
                    System.out.println("Revisor asignado con éxito");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error al asignar revisor");
                }
                System.out.println("Enviando email a \"" + revisor.getCorreo() + "\"...");
                String mensaje = "Estimado/a " + revisor.getNombre() + " " + revisor.getApellido() + ",\n\n" +
                        "Le informamos que ha sido seleccionado/a para revisar el artículo titulado \""
                        + articulo.getTitulo()
                        + "\".\n" +
                        "Resumen: " + articulo.getResumen() + "\n\n" +
                        "Contenido: " + articulo.getContenido() + "\n\n" +
                        "Palabras clave: " + String.join(", ", articulo.getPalabrasClave()) + "\n\n" +
                        "Se adjunta el PDF del mismo, por favor revise el artículo y envíe sus comentarios y sugerencias a la brevedad posible.\n\n"
                        +
                        "Saludos cordiales,\n" +
                        "Sistema de Asignación de Revisores";
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