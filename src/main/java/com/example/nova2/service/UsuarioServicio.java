package com.example.nova2.service;



import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.example.nova2.modelo.Publicacion;
import com.example.nova2.modelo.Usuario;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;


@Service
public class UsuarioServicio {

    private final Firestore fireStore;

    public UsuarioServicio(Firestore fireStore) {
        this.fireStore = fireStore;
    }
    
    public String obtenerIdPorCorreo(String correo) throws InterruptedException, ExecutionException {
        // Referencia a la colección de usuarios
        CollectionReference usuariosCollection = fireStore.collection("Usuarios_crud");
        
        // Consulta para buscar el usuario por correo
        ApiFuture<QuerySnapshot> query = usuariosCollection.whereEqualTo("correo", correo).get();

        // Obtener los documentos que coinciden con el correo
        QuerySnapshot querySnapshot = query.get();
        
        // Si no hay resultados, devolver un valor indicando que no se encontró
        if (querySnapshot.isEmpty()) {
            return "Usuario no encontrado";
        }

        // Si encontramos el usuario, obtenemos el ID del primer documento
        QueryDocumentSnapshot document = querySnapshot.getDocuments().get(0);
        return document.getId();  // Devolvemos el ID del usuario
    }

    
    // Método para actualizar la publicación del usuario
    public void actualizarPublicacion(String userId, Usuario usuario) throws InterruptedException, ExecutionException {
        DocumentReference usuarioDocRef = fireStore.collection("Usuarios_crud").document(userId);
        CollectionReference publicacionesRef = usuarioDocRef.collection("publicacion");

        // Actualizamos las publicaciones en la base de datos
        for (Publicacion publi : usuario.getPublicacion()) {
            publicacionesRef.add(publi);
        }
    }

    
    
    
    
    

    public List<Usuario> obtenerUsuarios() throws InterruptedException, ExecutionException {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            CollectionReference usuariosCollection = fireStore.collection("Usuarios_crud");
            ApiFuture<QuerySnapshot> querySnapshot = usuariosCollection.get();

            for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
                Usuario usuario = document.toObject(Usuario.class);
                usuario.setId(document.getId());  // Asignamos el ID al usuario

                // Ahora obtenemos las imágenes de la subcolección 'Imagen' para cada usuario
                CollectionReference publisCollection = document.getReference().collection("publicacion");
                ApiFuture<QuerySnapshot> imagenesSnapshot = publisCollection.get();
                
                List<Publicacion> publicaciones = new ArrayList<>();

                for (QueryDocumentSnapshot imagenDoc : imagenesSnapshot.get().getDocuments()) {
                    Publicacion publi = imagenDoc.toObject(Publicacion.class);
                    publi.setId(imagenDoc.getId());

     
                    List<String> imagenUrls = new ArrayList<>();
                    if (publi.getImagen() != null) {
                        imagenUrls.addAll(publi.getImagen()); 
                    }

                    
                    publi.setImagen(imagenUrls);  // Asignamos las URLs a la imagen
                    publicaciones.add(publi);
                }

                usuario.setPublicacion(publicaciones);  // Asignamos las imágenes al usuario
                usuarios.add(usuario);  // Añadimos el usuario completo con sus imágenes a la lista
            }

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error al obtener usuarios: " + e.getMessage());
            throw new RuntimeException("Error al obtener usuarios desde Firestore", e);
        }

        return usuarios;
    }






    
    
    
    
    
    
    
    
    
    
    public String crearUsuario(Usuario usuario, String uid) throws InterruptedException, ExecutionException {
        // Asignamos el uid al id del usuario
        usuario.setId(uid);

        // Guardamos el documento de usuario en Firestore con el uid como ID
        ApiFuture<WriteResult> addedDocRef = fireStore.collection("Usuarios_crud")
                                                             .document(uid) // Usamos el uid como ID del documento
                                                             .set(usuario); // Usamos el método set para guardar el documento
        
        return uid; // Devolvemos el uid (ID del usuario creado)
    }
	
	
	
	
   
}