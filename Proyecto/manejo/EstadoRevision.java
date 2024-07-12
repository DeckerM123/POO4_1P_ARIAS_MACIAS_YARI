package main.java.com.Proyecto.manejo;

public enum EstadoRevision {
    /**
     * Revisión de un artículo pendiente.
     */
    PENDIENTE("Pendiente"),
    /**
     * Revisión de un artículo realizada por el revisor asignado.
     */
    REVISADO("Revisado"),
    /**
     * Revisión de un artículo analizada por un editor.
     */
    ANALIZADO("Analizado"),
    /**
     * Revisión de un artículo verificada por un editor, que decidió si se publica o no.
     */
    VERIFICADO("Verificado");
    private final String descripcion;
    
    private EstadoRevision(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripciom() {
        return descripcion;
    }
}
