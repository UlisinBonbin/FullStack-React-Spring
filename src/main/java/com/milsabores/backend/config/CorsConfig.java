package com.milsabores.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/api/v1/**")

                // ¡LA LÍNEA CLAVE!
                // Permite peticiones SOLO desde este origen.
                .allowedOrigins("http://localhost:5173")

                // Permite estos métodos (incluyendo DELETE)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")

                // Permite todas las cabeceras
                .allowedHeaders("*")

                // Permite el envío de credenciales (importante para sesiones)
                .allowCredentials(true);
    }

}
