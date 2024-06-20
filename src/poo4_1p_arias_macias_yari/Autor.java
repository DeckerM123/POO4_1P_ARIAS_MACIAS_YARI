
package poo4_1p_arias_macias_yari;


public class Autor {
    // Atributos
    private String codigoID;
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private String institucion;
    private String campoInvestigacion;

    // Constructor vacío
    public Autor() {
    }

    // Constructor con parámetros
    public Autor(String codigoID, String nombre, String apellido, String correoElectronico, String institucion, String campoInvestigacion) {
        this.codigoID = codigoID;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.institucion = institucion;
        this.campoInvestigacion = campoInvestigacion;
    }

    // Getters y Setters
    public String getCodigoID() {
        return codigoID;
    }

    public void setCodigoID(String codigoID) {
        this.codigoID = codigoID;
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

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
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
}
