package com.example.nova2.modelo;



import java.util.List;

import com.google.cloud.firestore.annotation.DocumentId;

public class ChatMessageModel {
	@DocumentId
	
	private String id; // Identificador único del mensaje
    private String userName; // Nombre de usuario
    private String message; // Contenido del mensaje
    private String roomid; // ID de la sala de chat
    private List<String> attachments; // Lista de URLs de archivos adjuntos (si aplica)

    // Constructor vacío necesario para serialización o frameworks
    public ChatMessageModel() {
    }

    // Constructor con parámetros
    public ChatMessageModel( String userName, String message, String roomid, List<String> attachments) {
        
        this.userName = userName;
        this.message = message;
        this.roomid = roomid;
        this.attachments = attachments;
    }

    // Métodos getter y setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRoomId() {
        return roomid;
    }

    public void setRoomId(String roomId) {
        this.roomid = roomId;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "ChatMessageModel{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                ", roomId='" + roomid + '\'' +
                ", attachments=" + attachments +
                '}';
    }
}