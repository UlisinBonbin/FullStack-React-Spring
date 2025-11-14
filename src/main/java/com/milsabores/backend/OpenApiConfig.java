package com.milsabores.backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Pastelería Management API",
                version = "1.0",
                description = "API for the 'Pastelería Mil Sabores' web platform."
        ),
        security = @SecurityRequirement(name = "bearerAuth") // <-- Activa JWT globalmente
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT" // <-- Esto hace que Swagger muestre Authorize
)
public class OpenApiConfig {
}
