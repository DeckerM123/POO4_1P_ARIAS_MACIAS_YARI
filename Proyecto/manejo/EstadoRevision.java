package main.java.com.Proyecto.manejo;

public abstract class EstadoRevision {

    /**
     * Revisión de un artículo pendiente.
     */
    public static final String PENDIENTE = "Pendiente";

    /**
     * Revisión de un artículo realizada por el revisor asignado.
     */
    public static final String REVISADO = "Revisado";

    /**
     * Revisión de un artículo analizada por un editor.
     */
    public static final String ANALIZADO = "Analizado";

    /**
     * Revisión de un artículo verificada por un editor, que decidió si se publica o no.
     */
    public static final String VERIFICADO = "Verificado";
}
