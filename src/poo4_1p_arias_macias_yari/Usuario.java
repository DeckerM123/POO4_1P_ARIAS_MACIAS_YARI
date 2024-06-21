public class usuario {
    protected String nombre;
    private String apellido;
    protected String correo;
    private char rol;
public usuario(String nombre,String apellido,String correo,char rol){
    this.nombre=nombre;
    this.apellido=apellido;
    this.correo=correo;
    this.rol=rol;
    
}
public String getNombre(){return this.nombre;}
public void setNombre(String nombre){this.nombre=nombre;}
public void setApellido(String apellido){this.apellido=apellido;}
public String getApellido(){return this.apellido;}
public String getCorreo(){return this.correo;}
public void setCorreo(String correo){this.correo=correo;}
public char getRol(){return this.rol;}
public void setRol(char rol){this.rol=rol;}

public void someterArticulo(Articulo articulo){}
public void GenerarCorreo(){}
}
