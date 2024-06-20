/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo_yari_macias_arias;

/**
 *
 * @author steven
 */
public class Editor extends Usuario{
     private String nombreJournal;
     private String contrasena;

    // Constructores
    public Editor(String nombre, String apellido, String correo, String user, String nombreJournal, String contrasena){
        super(nombre, apellido, correo, user, "E");
        this.nombreJournal = nombreJournal;
        this.contrasena=contrasena;
    }

    // Getters y Setters
    public String getNombreJournal() {
        return nombreJournal;
    }

    public void setNombreJournal(String nombreJournal) {
        this.nombreJournal = nombreJournal;
    }
    
     public String contrasena() {
        return nombreJournal;
    }

    public void contrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}
