package main.java.com.Proyecto.manejo;

import java.io.IOException;
import java.util.Scanner;

import main.java.com.Proyecto.Users.Editor;
import main.java.com.Proyecto.Users.Revisor;
import main.java.com.Proyecto.Users.Usuario;

public abstract class Verificar {
    private static Scanner sc = new Scanner(System.in);

    /**
     * Inicia sesión de un usuario
     * El usuario debe ingresar su nombre de usuario y contraseña
     * El usuario puede ser un editor o un revisor
     * @return Usuario que inicia sesión o null si no se pudo iniciar sesión
     */
    public static Usuario iniciarSesion() {
        Usuario user;
        System.out.print("Usuario: ");
        String usuario = sc.nextLine().strip();
        System.out.print("Contraseña: ");
        String contrasena = sc.nextLine().strip();
        try {
            String[] usuarios = ManejoDatos.leerArchivo(Archivo.USUARIOS).split("\n");
            for (int i = 0; i < usuarios.length; i++) {
                String[] datosUsuario = usuarios[i].split(",");
                String username = datosUsuario[0];
                String password = datosUsuario[1];
                String tipo = datosUsuario[2];
                String codigo = datosUsuario[3];
                if (usuario.equals(username) && contrasena.equals(password)) {
                    switch (tipo) {
                        case "E":
                            String editorBuscado = ManejoDatos.buscarArchivo(Archivo.EDITORES, codigo);
                            if (editorBuscado != null) {
                                String[] editor = editorBuscado.split(",");
                                user = new Editor(editor[0], editor[1], editor[2], editor[3], editor[4]);
                            } else {
                                System.out.println("Revisar archivos de usuarios y editores");
                                return null;
                            }
                            break;
                        case "R":
                            String revisorBuscado = ManejoDatos.buscarArchivo(Archivo.REVISORES, codigo);
                            if (revisorBuscado != null) {
                                String[] revisor = revisorBuscado.split(",");
                                user = new Revisor(revisor[0], revisor[1], revisor[2], revisor[3], revisor[4],
                                        Integer.parseInt(revisor[5]));
                            } else {
                                System.out.println("Revisar archivos de usuarios y revisores");
                                return null;
                            }
                            break;
                        default:
                            System.out.println("Tipo de usuario no válido");
                            return null;
                    }
                    System.out.println("Inicio de sesión exitoso");
                    return user;
                }
            }
            System.out.println("Usuario o contraseña incorrectos");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al iniciar sesión");
        }
        return null;
    }
}
