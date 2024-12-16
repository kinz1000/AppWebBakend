package com.example.nova2.modelo;

import java.util.Objects;

public class ChatMessage {
    private String message;
    private String user;

    // Constructor con todos los argumentos
    public ChatMessage(String message, String user) {
        this.message = message;
        this.user = user;
    }

    // Métodos getter y setter para "message"
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Métodos getter y setter para "user"
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    // Método toString
    @Override
    public String toString() {
        return "ChatMessage{" +
                "message='" + message + '\'' +
                ", user='" + user + '\'' +
                '}';
    }

    // Métodos equals y hashCode (generados automáticamente)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessage that = (ChatMessage) o;
        return Objects.equals(message, that.message) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, user);
    }
}