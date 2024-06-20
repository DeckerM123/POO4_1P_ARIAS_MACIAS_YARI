public class Revisor extends Usuario{
     private String especialidad;
     private String contrasena;
     private int numArticulo;

    // Constructores
    public Revisor(String nombre, String apellido, String correo, String user, String especialidad, String contrasena,int numArticulo){
        super(nombre, apellido, correo, user, "R");
        this.especialidad = especialidad;
        this.contrasena=contrasena;
        this.numArticulo=numArticulo;
    }

    // Getters y Setters
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
     public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public int getNumArticulo() {
        return numArticulo;
    }

    public void setNumArticulos(int numArticulo) {
        this.numArticulo = numArticulo;
    }
}
