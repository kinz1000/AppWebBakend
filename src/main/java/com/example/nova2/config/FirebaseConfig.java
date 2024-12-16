package com.example.nova2.config;





import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;



import jakarta.annotation.PostConstruct;



@Configuration
public class FirebaseConfig{

@PostConstruct
	public void initialize(){   //configuracion de la base de datos 
								//en resources la clave es el modo por el que se conecta , aqui no se hace uso de app.propieties
	
							
	        try {
	            FileInputStream refreshToken = new FileInputStream("src/main/resources/clave_firebase.json");

	            FirebaseOptions options = FirebaseOptions.builder()
	                .setCredentials(GoogleCredentials.fromStream(refreshToken))
	                .setDatabaseUrl("https://BaseCloud.firebaseio.com/")
	                .setStorageBucket("basecloud-61f4e.appspot.com")
	                .build();
	            
	    
	            
	            FirebaseApp.initializeApp(options);
	       
	            System.out.println("Firebase initialized successfully");
	        } catch (IOException e) {
	            e.printStackTrace(); // Manejo básico de errores
	        }
	    }




@Bean
 Firestore fireStore() {
    return FirestoreClient.getFirestore(); // Devuelve la instancia de Firestore
}
@Bean
 Storage storage() {
    // Inicializa el servicio de Google Cloud Storage con las credenciales predeterminadas
    return StorageOptions.getDefaultInstance().getService();
}
}
	