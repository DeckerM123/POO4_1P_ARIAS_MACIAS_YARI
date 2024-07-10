package main.java.com.Proyecto.Users;

import java.io.IOException;
import java.util.ArrayList;

import main.java.com.Proyecto.manejo.ManejoDatos;
import main.java.com.Proyecto.Sistema;
import main.java.com.Proyecto.manejo.Correo;
import main.java.com.Proyecto.manejo.EstadoRevision;
import main.java.com.Proyecto.manejo.Archivo;

public class Editor extends Usuario {
    private String nombJournal;

    /**
     * Instancia un nuevo editor
     * 
     * @param codigo      Código del editor
     * @param nombre      Nombre del editor
     * @param apellido    Apellido del editor
     * @param correo      Correo del editor
     * @param nombJournal Journal al que pertenece el editor
     */
    public Editor(String codigo, String nombre, String apellido, String correo, String nombJournal) {
        super(codigo, nombre, apellido, correo);
        this.nombJournal = nombJournal;
    }

    /**
     * @return String
     */
    public String getNombJournal() {
        return nombJournal;
    }

    public void setNombJournal(String nombJournal) {
        this.nombJournal = nombJournal;
    }

    @Override
    /**
     * Devuelve una representación en cadena del Editor
     */
    public String toString() {
        return nombre + "," + apellido + "," + correo + "," + nombJournal;
    }

    @Override
    /**
     * Genera un correo electrónico con informacion necesaria
     * y lo envía al destinatario
     * 
     * @param destinatario Correo del destinatario
     * @param asunto       Asunto del correo
     * @param mensaje      Cuerpo del correo
     * @return true si el correo se envió con éxito, false en caso contrario
     */
    protected boolean generarCorreo(String destinatario, String asunto, String mensaje) {
        return Correo.enviar(destinatario, asunto, mensaje);
    }

    @Override
    /**
     * Editor decide si aceptar o rechazar un artículo
     * y notifica al autor
     * 
     * @param articulo Artículo a revisar
     */
    public void decidirSobreArticulo(Articulo articulo) {
        System.out.println("¿Desea publicar el artículo? S\\N");
        String respuesta = Sistema.sc.nextLine().strip();
        String decision = respuesta.equalsIgnoreCase("S") ? "Aceptar" : "Rechazar";
        String registro = EstadoRevision.VERIFICADO + ";" + codigo + ";" + articulo.getCodigo() + ";" + decision;
        try {
            ArrayList<String> revisionesViejas = ManejoDatos.busquedaAvanzada(Archivo.REVISIONES,
                    articulo.getCodigo());

            for (int i = 0; i < revisionesViejas.size(); i++) {
                String revisionVieja = revisionesViejas.get(i);
                String[] revisionModificada = revisionVieja.split(";");
                try {
                    EstadoRevision estadoRevision = EstadoRevision.valueOf(revisionModificada[0].toUpperCase());

                    if (!estadoRevision.equals(EstadoRevision.PENDIENTE)) {
                        revisionModificada[0] = EstadoRevision.ANALIZADO.getDescripcion();
                        ManejoDatos.modificarLineaDeArchivo(Archivo.REVISIONES, revisionVieja,
                                String.join(";", revisionModificada));
                    }
                } catch (IllegalArgumentException e) {
                    // Manejar el caso donde el valor en revisionModificada[0] no coincide con
                    // ningún valor del enum
                    System.out.println("El estado de revisión no es válido: " + revisionModificada[0]);
                }
            }

            ManejoDatos.escribirArchivo(Archivo.REVISIONES, registro);
            System.out.println("Registro exitoso");

            String[] autores = ManejoDatos.leerArchivo(Archivo.AUTORES).split("\n");
            for (int i = 0; i < autores.length; i++) {
                String autor = autores[i];
                String[] datosAutor = autor.split(";");
                if (datosAutor[0].equals(articulo.getAutor())) {
                    String primerRevision = revisionesViejas.isEmpty() ? "No hay revisiones" : revisionesViejas.get(0);
                    String ultimaRevision = revisionesViejas.isEmpty() ? "No hay revisiones"
                            : revisionesViejas.get(revisionesViejas.size() - 1);

                    String mensaje = String.format(
                        "Hola"+ datosAutor[1] + datosAutor[2],"\n\n" +
                        "La decisión tomada por el editor es"+decision.toUpperCase()+ "el artículo"+articulo.getTitulo()+".\n\n" +
                        "Le enviamos los comentarios realizados por los revisores:\n" + primerRevision + ultimaRevision +
                        "Saludos desde el equipo de" +nombJournal+"\n Muchas gracias");

                    boolean enviado = generarCorreo(datosAutor[3], "Decisión sobre Art#" + articulo.getCodigo(),
                            mensaje);
                    if (enviado) {
                        System.out.println("Se envio el correo con exito");
                    } else {
                        System.out.println("Error en el envio de correo");
                    }
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error en el registro de decision");
        }
    }
}
