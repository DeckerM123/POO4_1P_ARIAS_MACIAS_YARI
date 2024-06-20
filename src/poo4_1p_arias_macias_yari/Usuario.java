public class usuario {
    private String nombre;
    private String apellido;
    private String correo;
    private char rol;
public usuario(String nombre,String apellido,String correo,char rol){
    this.nombre=nombre;
    this.apellido=apellido;
    this.correo=correo;
    this.rol=rol;
    
}
public String getnombre(){return this.nombre;}
public void setnombre(String nombre){this.nombre=nombre;}
public void setApellido(String apellido){this.apellido=apellido;}
public String getApellido(){return this.apellido;}
public String getCorreo(){return this.correo;}
public void setCorreo(String correo){this.correo=correo;}
public char getRol(){return this.rol;}
public void setRol(char rol){this.rol=rol;}

public void someterArticulo(Articulo articulo){}
public void GenerarCorreo(){}
}
