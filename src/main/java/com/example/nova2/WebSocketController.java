package com.example.nova2;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.nova2.modelo.ChatMessage;
import com.example.nova2.modelo.ChatMessageModel;
import com.example.nova2.service.ChatServicio;

@Controller
@CrossOrigin("")
public class WebSocketController {

    @Autowired
    private ChatServicio cS ;

    
    
  
    
    
   
    @MessageMapping("/chat/{roomId}") // Usamos el mapeo de la URL
    @SendTo("/topic/{roomId}") // El tópico será dinámico con el roomId
    public ChatMessage chat(@DestinationVariable String roomId, ChatMessage message) {
        System.out.println("Mensaje recibido: " + message);

        // Crear el modelo para guardar en Firestore
        ChatMessageModel chatMessageModel = new ChatMessageModel();
        chatMessageModel.setUserName(message.getUser());
        chatMessageModel.setMessage(message.getMessage());
        chatMessageModel.setRoomId(roomId);

        try {
            // Guardar en Firestore (si todo va bien)
            cS.save(chatMessageModel);
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Error al guardar el mensaje: " + e.getMessage());
            e.printStackTrace();
            // Opcional: puedes enviar un mensaje de error a los suscriptores si es necesario
        }

        // Devolver el mensaje para que se envíe a los suscriptores de /topic/{roomId}
        return new ChatMessage(message.getMessage(), message.getUser());
    }
    // REST API endpoint: obtener todos los mensajes de una sala específica
    @GetMapping("/api/chat/{roomId}")
    public ResponseEntity<List<ChatMessageModel>> getAllChatMessages(@PathVariable String roomId) {
        try {
            List<ChatMessageModel> result = cS.findByRoomId(roomId);
            return ResponseEntity.ok(result);
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Error al recuperar los mensajes: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).build(); // Internal Server Error si algo falla
        }
    }
}