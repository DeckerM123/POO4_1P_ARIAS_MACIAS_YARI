package main.java.com.Proyecto.Users;

import java.io.IOException;

import main.java.com.Proyecto.manejo.ManejoDatos;
import main.java.com.Proyecto.Sistema;
import main.java.com.Proyecto.manejo.Correo;
import main.java.com.Proyecto.manejo.EstadoRevision;
import main.java.com.Proyecto.manejo.Archivo;

public class Revisor extends Usuario {
    private String especialidad;
    private int articulosRevisados;

    /**
     * Instancia un nuevo revisor
     * @param codigo Código del revisor
     * @param nombre Nombre del revisor
     * @param apellido Apellido del revisor
     * @param correo Correo del revisor
     * @param especialidad Especialidad del revisor
     * @param articulosRevisados Número de artículos revisados por el revisor
     */
    public Revisor(String codigo, String nombre, String apellido, String correo, String especialidad, int articulosRevisados) {
        super(codigo, nombre, apellido, correo);
        this.especialidad = especialidad;
        this.articulosRevisados = articulosRevisados;
    }

    
    /** 
     * @return String
     */
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public int getArticulosRevisados() {
        return articulosRevisados;
    }

    public void setArticulosRevisados(int numeroArticulosRevisados) {
        this.articulosRevisados = numeroArticulosRevisados;
    }

    @Override
    /**
     * Devuelve una representación en cadena del Revisor
     */
    public String toString() {
        return "Revisor: " + nombre + " " + apellido + "\nCorreo: " + correo + "\nEspecialidad: " + especialidad + "\nArtículos revisados: " + articulosRevisados;
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
    public boolean generarCorreo(String destinatario, String asunto, String mensaje) {
        return Correo.enviar(destinatario, asunto, mensaje);
    }

    @Override
    /**
     * Revisor ingresa comentarios y decide si aceptar o rechazar un artículo
     * @param articulo Artículo a revisar
     */
    public void decidirSobreArticulo(Articulo articulo) {
        System.out.println("Ingrese sus comentarios:");
        String comentarios = Sistema.sc.nextLine().strip();
        System.out.println("¿Desea aceptar el artículo? S\\N");
        String respuesta = Sistema.sc.nextLine().strip();
        String decision = respuesta.equalsIgnoreCase("S") ? "Articulo aceptado" : "Articulo rechazado";
        String registro = EstadoRevision.REVISADO + ";" + codigo + ";" + articulo.getCodigo() + ";" + comentarios + ";" + decision;
        try {
            String revisorVieja = ManejoDatos.busquedaEnArchivo(Archivo.REVISORES, codigo);
            String[] revisorNueva = revisorVieja.split(",");
            revisorNueva[5] = String.valueOf(Integer.parseInt(revisorNueva[5]) + 1);
            ManejoDatos.modificarLineaDeArchivo(Archivo.REVISORES, revisorVieja,","+revisorNueva);

            String revisionVieja = ManejoDatos.busquedaEnArchivo(Archivo.REVISIONES,
                    codigo + ";" + articulo.getCodigo());
            ManejoDatos.modificarLineaDeArchivo(Archivo.REVISIONES, revisionVieja, registro);

            System.out.println("La revision se envio con éxito");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error en envio de revision");
        }
    }
}
