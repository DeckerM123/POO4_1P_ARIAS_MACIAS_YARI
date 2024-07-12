package main.java.com.Proyecto.Usuarios;

public abstract class Usuario {
    protected String codigo;
    protected String nombre;
    protected String apellido;
    protected String correo;
    protected String usuario;
    protected String contrasena;

    /**
     * Instancia un nuevo usuario
     * @param codigo Código del usuario
     * @param nombre Nombre del usuario
     * @param apellido Apellido del usuario
     * @param correo Correo del usuario
     */
    public Usuario(String codigo, String nombre, String apellido, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.codigo = codigo;
    }

    
    /** 
     * @return String
     */

     public String getCodigo() {
        return codigo;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    /**
     * Devuelve un entero hasheado basado en el nombre, apellido y correo del usuario
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

    protected abstract boolean crearCorreo(String destinatario, String asunto, String mensaje);
    
    protected abstract void decisionDeArticulo(Articulo articulo);
}
