
package poo4_1p_arias_macias_yari;


public class Autor extends Usuario{
    // Atributos
    private String codigoID;
    private String institucion;
    private String campoInvestigacion;

    // Constructor vacío
    public Autor() {
    }

    // Constructor con parámetros
    public Autor(String codigoID, String institucion, String campoInvestigacion) {
        this.codigoID = codigoID;
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
