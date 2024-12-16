package com.example.nova2.service;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;



import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class StorageServicio { //En vez de utilizar repositorios y todo lo hacemos con servicios mas compacto
	
	
	   private static final String CREDENTIALS_PATH = "src/main/resources/clave_firebase.json";
	   
	   
	  private final Storage storage; // Interacción con Google Cloud Storage
	    private final String bucketName ="basecloud-61f4e.appspot.com";// Nombre del bucket de Firebase Storage
 
	
    
	




	    public String uploadImage3(MultipartFile file, String fileName) throws IOException {
	        // Verificar si el archivo está vacío
	        if (file.isEmpty()) {
	            throw new IOException("El archivo está vacío.");
	        }

	        // Crear un ID único para el archivo dentro del bucket
	        BlobId blobId = BlobId.of(bucketName, fileName);
	        
	        // Crear BlobInfo con el tipo de contenido especificado
	        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
	                .setContentType("image/jpeg") // Especificar el tipo de contenido
	                .build();
	        
	        // Subir el archivo al bucket
	        Blob blob = storage.create(blobInfo, file.getBytes());
	        
	        // Devolver la URL pública del archivo subido
	        return blob.getMediaLink();
	    }
	   
	
	    public String uploadImage2(MultipartFile file, String fileName) throws IOException {
	        if (file.isEmpty()) {
	            throw new IOException("El archivo está vacío.");
	        }

	        // Crear un ID único para el archivo dentro del bucket
	        BlobId blobId = BlobId.of(bucketName, fileName);
	        
	        // Crear BlobInfo con el tipo de contenido especificado
	        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
	                .setContentType("image/jpeg") // Especificar el tipo de contenido
	                .build();
	        
	        // Subir el archivo al bucket
	        Blob blob = storage.create(blobInfo, file.getBytes());
	        
	        // Devolver el enlace en formato gs://
	        return "gs://" + bucketName + "/" + fileName; // Este es el formato "gs://"
	    }


	  


	    public StorageServicio(Storage storage) throws FileNotFoundException, IOException {
			super();
			
			 this.storage = StorageOptions.newBuilder()
			            .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(CREDENTIALS_PATH)))
			            .build()
			            .getService();
		}











		








	    public String obtenerURL(String fileName) {
	        Blob blob = storage.get(BlobId.of(bucketName, fileName));
	        return blob != null ? blob.signUrl(365, TimeUnit.DAYS).toString() : null;
	    }











}