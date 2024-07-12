package main.java.com.Proyecto.Users;

import java.io.IOException;

import main.java.com.Proyecto.manejo.Archivo;
import main.java.com.Proyecto.manejo.ManejoDatos;

public class Autor {
    private String codigo;
    private String nombre;
    private String apellido;
    private String correo;
    private String institucion;
    private String campoInvestigacion;

    /**
     * Instancia un nuevo autor
     * @param nombre Nombre del autor
     * @param apellido Apellido del autor
     * @param correo Correo del autor
     * @param institucion Institución a la que pertenece el autor
     * @param campoInvestigacion Campo de investigación del autor
     */
    public Autor(String nombre, String apellido, String correo, String institucion,
            String campoInvestigacion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.institucion = institucion;
        this.campoInvestigacion = campoInvestigacion;
        this.codigo = this.hashCode() + "";
    }

    
    /** 
     * @return String
     *
     */
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getCampoInvestigacion() {
        return campoInvestigacion;
    }

    public void setCampoInvestigacion(String campoInvestigacion) {
        this.campoInvestigacion = campoInvestigacion;
    }

    @Override
    /**
     * Devuelve un entero hasheado basado en el nombre, apellido y correo del autor
     * @return Entero hasheado
     */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
        result = prime * result + ((correo == null) ? 0 : correo.hashCode());
        return result;
    }

    @Override
    /**
     * Devuelve una representación en cadena del autor
     */
    public String toString() {
        return  codigo+","+ nombre+","+ apellido+","+ correo+","+ institucion+","+ campoInvestigacion;
    }

    /**
     * Registra el artículo en el archivo de artículos
     * @param articulo Artículo a registrar
     * @return true si el artículo se registró con éxito, false en caso contrario
     */
    public boolean someterArticulo(Articulo articulo) {
        try {
            ManejoDatos.escribirArchivo(Archivo.ARTICULOS, articulo.toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Registra al autor en el archivo de autores
     */
    public void registrarse(){
        try {
            ManejoDatos.escribirArchivo(Archivo.AUTORES, this.toString());
            System.out.println("Registro hecho con éxito");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al tratar de registrar");
        }
    }

}
