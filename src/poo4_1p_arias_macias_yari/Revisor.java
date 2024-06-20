public class Revisor extends Usuario{
     private String especialidad;
     private String contrasena;
     private int numArticulos;

    // Constructores
    public Revisor(String nombre, String apellido, String correo, String user, String especialidad, String contrasena,int numArticulos){
        super(nombre, apellido, correo, user, "R");
        this.especialidad = especialidad;
        this.contrasena=contrasena;
        this.numArticulos=numArticulos;
    }

    // Getters y Setters
    public String getespecialidad() {
        return especialidad;
    }

    public void setespecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
     public String getcontrasena() {
        return contrasena;
    }

    public void setcontrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public int getnumArticulos() {
        return numArticulos;
    }

    public void setnumArticulos(int numArticulos) {
        this.numArticulos = numArticulos;
    }

