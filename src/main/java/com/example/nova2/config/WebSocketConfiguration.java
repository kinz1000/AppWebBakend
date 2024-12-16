package com.example.nova2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**")
	            .allowedOrigins("http://localhost:4200")  // Frontend en el puerto 4200
	            .allowedMethods("GET", "POST", "PUT", "DELETE") 
	            .allowedOrigins("*");  ; // Métodos permitidos
	       
	}
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat-socket").setAllowedOrigins("/http://localhost:4200").withSockJS(); // Endpoint de WebSocket
    }
    
    
    
   
    
}