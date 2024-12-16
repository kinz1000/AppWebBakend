package com.example.nova2.modelo;



import java.util.List;

import com.google.cloud.firestore.annotation.DocumentId;



public class Usuario {

    @DocumentId
    private String id;
    private String nombre;
    private String nick;
    private String correo;
    private String password;
    private List<Publicacion> publicacion;  //esto dejarlo asi
    // Constructor vacío necesario para Firebase
    public Usuario() {
    }

    // Constructor con parámetros
    public Usuario(String nombre, String nick, String correo, String password, List<Publicacion> p) {
        this.nombre = nombre;
        this.nick = nick;
        this.correo = correo;
        this.password = password;
        this.setPublicacion(p);  // Asignación de la lista de imágenes
    }

    // Métodos getter y setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public List<Publicacion> getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(List<Publicacion> publicacion) {
		this.publicacion = publicacion;
	}

  

	
}