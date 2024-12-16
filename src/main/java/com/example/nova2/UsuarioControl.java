package com.example.nova2;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.nova2.modelo.Usuario;
import com.example.nova2.service.StorageServicio;
import com.example.nova2.service.UsuarioServicio;
@RestController
@RequestMapping("/api")  
public class UsuarioControl {
	@Autowired
	private UsuarioServicio us;
	
	@Autowired
	private StorageServicio sS;
	
	
	
	
	
	@PostMapping("/uploadImage")
	public ResponseEntity<Map<String, String>> uploadImage(@RequestParam MultipartFile file) {
	    try {
	        // Generar un nombre único para el archivo
	        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
	        // Subir la imagen y obtener la URL
	        String imageUrl = sS.uploadImage2(file, fileName);

	        // Crear un Map con la respuesta JSON
	        Map<String, String> response = new HashMap<>();
	        response.put("imageUrl", imageUrl);  // URL de la imagen subida

	        // Devolver la respuesta en formato JSON con código HTTP 200 OK
	        return ResponseEntity.ok(response);
	    } catch (IOException e) {
	        // Manejo de errores, en caso de que ocurra un problema al subir la imagen
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("message", "Error al subir la imagen: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
	   

	
	@PutMapping("/createPubli/{userId}")
	public ResponseEntity<Map<String, String>> updateUser(@PathVariable String userId, @RequestBody Usuario usuario) {
	    try {
	        // Llamada al servicio para actualizar las publicaciones del usuario en Firestore
	        us.actualizarPublicacion(userId, usuario);
	        // Devolver mensaje de éxito en formato JSON
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "Publicación actualizada exitosamente.");
	        return ResponseEntity.ok(response);
	    } catch (InterruptedException | ExecutionException e) {
	        // Devolver error en formato JSON
	        Map<String, String> response = new HashMap<>();
	        response.put("error", "Error al actualizar las publicaciones: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}
	
	  @GetMapping("/getUserIdByEmail")
	    public ResponseEntity<String> getUserIdByEmail(@RequestParam String correo) {
	        try {
	            String usuarioId = us.obtenerIdPorCorreo(correo);
	            if ("Usuario no encontrado".equals(usuarioId)) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
	            }
	            return ResponseEntity.ok(usuarioId);
	        } catch (InterruptedException | ExecutionException e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener el ID");
	        }
	    }
	
	
	@GetMapping("/getImageUrl")
	public ResponseEntity<Map<String, String>> obtenerUrlImagen(@RequestParam String fileName) {
	    String url = sS.obtenerURL(fileName);
	    if (url != null) {
	        Map<String, String> response = new HashMap<>();
	        response.put("url", url);
	        return ResponseEntity.ok(response);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Imagen no encontrada"));
	    }
	}
	
	
	
	
	@PostMapping("/create/User")
	   public ResponseEntity<Void> crearUsuario(@RequestBody Usuario usuario, @RequestParam String uid) {
        try {
            us.crearUsuario(usuario, uid); // Usamos el uid para guardar el usuario en Firestore
            return ResponseEntity.status(HttpStatus.CREATED).build(); // Devuelve un 201 (Creado)
        } catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(null); // Maneja los errores si ocurren
        }
    }
	 @GetMapping("/obtenerUsuarios")//el metodo que obtiene todos los usuarios
	    public ResponseEntity<List<Usuario>> obtenerUsuarios() throws InterruptedException, ExecutionException {
	        List<Usuario> usuarios = us.obtenerUsuarios();
	        return ResponseEntity.ok(usuarios);
	    }
	 @GetMapping("/test")//un metodo de testeo para ver sie sta bien conectado el back con el front y bd
	 public ResponseEntity<String> test() {
	     return ResponseEntity.ok("El servidor está funcionando correctamente");
	 }
	
}