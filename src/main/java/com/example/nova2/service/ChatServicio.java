package com.example.nova2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.example.nova2.modelo.ChatMessageModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

@Service
public class ChatServicio {
	
	  private final Firestore fireStore;

	    public ChatServicio(Firestore fireStore) {
	        this.fireStore = fireStore;
	    }
	    
	    public String save(ChatMessageModel chatMessageModel) throws ExecutionException, InterruptedException {
	        CollectionReference chatsCollection = fireStore.collection("Chats");
	        
	        // Guardar el mensaje
	        ApiFuture<DocumentReference> future = chatsCollection.add(chatMessageModel);
	        String documentId = future.get().getId();
	        System.out.println("Mensaje guardado con ID: " + documentId);
	        // Retornar el ID generado
	        return documentId;
	    }

	    // Método para obtener mensajes por roomId
	    public List<ChatMessageModel> findByRoomId(String roomId) throws ExecutionException, InterruptedException {
	        CollectionReference chatsCollection = fireStore.collection("Chats");

	        // Consulta por roomId
	        ApiFuture<QuerySnapshot> query = chatsCollection.whereEqualTo("roomid", roomId).get();
	        
	        List<QueryDocumentSnapshot> documents = query.get().getDocuments();
	        List<ChatMessageModel> chatMessages = new ArrayList<>();
	        
	        // Mapear los documentos encontrados al modelo
	        for (QueryDocumentSnapshot document : documents) {
	            ChatMessageModel chatMessage = document.toObject(ChatMessageModel.class);
	            chatMessages.add(chatMessage);
	        }

	        // Devolver la lista de mensajes (puede estar vacía si no hay resultados)
	        return chatMessages;
	    }
	}



