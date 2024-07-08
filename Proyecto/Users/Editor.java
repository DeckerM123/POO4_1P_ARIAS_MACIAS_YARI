package main.java.com.Proyecto.Users;

import java.io.IOException;
import java.util.ArrayList;

import main.java.com.Proyecto.Sistema;
import main.java.com.Proyecto.Manejo.Archivo;
import main.java.com.Proyecto.Manejo.Correo;
import main.java.com.Proyecto.Manejo.ManejarArchivo;
import main.java.com.Proyecto.Manejo.Revisar;

public class Editor extends Usuario {
    private String journal;

    /**
     * Instancia un nuevo editor
     * @param codigo Código del editor
     * @param nombre Nombre del editor
     * @param apellido Apellido del editor
     * @param correo Correo del editor
     * @param journal Journal al que pertenece el editor
     */
    public Editor(String codigo, String nombre, String apellido, String correo, String journal) {
        super(codigo, nombre, apellido, correo);
        this.journal = journal;
    }

    
    /** 
     * @return String
     */
    public String getJournal() {
        return journal;
    }
    
    public void setJournal(String journal) {
        this.journal = journal;
    }

    @Override
    /**
     * Devuelve una representación en cadena del Editor
     */
    public String toString() {
        return String.format("Editor %s %s\nCorreo: %s\n" +
        "Journal: %s",
                nombre, apellido, correo, journal);
    }

    @Override
    /**
     * Genera un correo electrónico con el asunto y mensaje dados
     * y lo envía al destinatario
     * @param destinatario Correo del destinatario
     * @param asunto Asunto del correo
     * @param mensaje Cuerpo del correo
     * @return true si el correo se envió con éxito, false en caso contrario
     */
    protected boolean generarCorreo(String destinatario, String asunto, String mensaje) {
        return Correo.enviar(destinatario, asunto, mensaje);
    }

    @Override
    /**
     * Editor decide si aceptar o rechazar un artículo
     * y notifica al autor
     * @param articulo Artículo a revisar
     */
    public void decidirSobreArticulo(Articulo articulo) {
        System.out.println("¿Publicar el artículo? S\\N");
        String respuesta = Sistema.sc.nextLine().strip();
        String decision = respuesta.equalsIgnoreCase("S") ? "Aceptar" : "Rechazar";
        String registro = Revisar.VERIFICADO + ";" + codigo + ";" + articulo.getCodigo() + ";" + decision;
        try {
            ArrayList<String> revisionesViejas = Persistencia.busquedaAvanzada(Archivos.REVISIONES, articulo.getCodigo());
            
            for (int i = 0; i < revisionesViejas.size(); i++) {
                String revisionVieja = revisionesViejas.get(i);
                String[] revisionModificada = revisionVieja.split(";");
                if(!revisionModificada[0].equals(Revisar.PENDIENTE)){
                    revisionModificada[0] = Revisar.ANALIZADO;
                    Persistencia.modificarLineaDeArchivo(Archivos.REVISIONES, revisionVieja, String.join(";", revisionModificada));
                }
            }
            Persistencia.escribirArchivo(Archivos.REVISIONES, registro);
            System.out.println("Decisión registrada con éxito");

            String[] autores = Persistencia.leerArchivo(Archivos.AUTORES).split("\n");
            for (int i = 0; i < autores.length; i++) {
                String autor = autores[i];
                String[] datosAutor = autor.split(",");
                if (datosAutor[0].equals(articulo.getAutor())) {
                    String mensaje = "Estimado/a " + datosAutor[1] + " " + datosAutor[2] + ",\n\n" +
                            "La decisión del editor es " + decision.toUpperCase() + " el artículo \"" + articulo.getTitulo() +"\".\n" + 
                            "Se adjuntan los comentarios de los revisores:\n" +
                            "\t- \"" + revisionesViejas.getFirst() + "\"\n" +
                            "\t- \"" + revisionesViejas.getLast() + "\"\n" +
                            "\n\n" +
                            "Saludos cordiales,\nDepartamento de Edición y Publicación de " + journal;
                    boolean eviado = generarCorreo(datosAutor[3], "Decisión sobre Art#" + articulo.getCodigo(), mensaje);
                    if (eviado) {
                        System.out.println("Correo enviado con éxito");
                    } else {
                        System.out.println("Error al enviar el correo");
                    }
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al registrar la decisión");
        }
    }
}
