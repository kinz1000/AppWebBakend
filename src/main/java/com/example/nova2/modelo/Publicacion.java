package com.example.nova2.modelo;

import java.util.List;

import com.google.cloud.firestore.annotation.DocumentId;
public class Publicacion {

    @DocumentId
    private String id; // Identificador único de la imagen en Firestore
    private String descripcion; // Descripción de la imagen
    private List<String> imagen; // Lista de URLs de imágenes
    private String tipo; //ofrezco o busco
    private String titulo; // Título de la imagen
    private String ubicacion; // Ubicación relacionada con la imagen (puede ser un lugar o un valor de geolocalización)

    // Constructor vacío necesario para Firestore
    public Publicacion() {
    }

    // Constructor con parámetros
    public Publicacion(String descripcion, List<String> imagen, String tipo, String titulo, String ubicacion) {
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.tipo = tipo;
        this.titulo = titulo;
        this.ubicacion = ubicacion;
    }

    // Métodos getter y setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getImagen() {
        return imagen;
    }

    public void setImagen(List<String> imagen) {
        this.imagen = imagen;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "Imagen{" +
                "id='" + id + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imagen=" + imagen + 
                ", tipo='" + tipo + '\'' +
                ", titulo='" + titulo + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}