package com.example.nova2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Nova2Application {

	public static void main(String[] args) {
		SpringApplication.run(Nova2Application.class, args);
	}

	
	
	@Configuration
	public class WebConfig implements WebMvcConfigurer {

	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**") // Permitir todas las rutas
	                .allowedOrigins("http://localhost:4200") // URL de tu frontend
	                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
	                .allowedHeaders("*") // Permitir todos los headers
	                .allowCredentials(true); // Permitir credenciales
	    }
	}
}
