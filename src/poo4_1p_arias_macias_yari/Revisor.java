public class Revisor extends Usuario{
     private String especialidad;
     private String contrasena;
     private int nunArticulos;

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
    
     public String contrasena() {
        return contrasena;
    }

    public void contrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public String getnumArticulos() {
        return numArticulos;
    }

    public void setnumArticulos(String numArticulos) {
        this.numArticulos = numArticulos;
    }

}
