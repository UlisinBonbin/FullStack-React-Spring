package com.milsabores.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    // ================================
    //   ✅ CONFIGURACIÓN CORS
    // ================================
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    // ================================
    //   🔐 SECURITY FILTER CHAIN
    // ================================
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {}) // IMPORTANTE
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // SWAGGER PÚBLICO
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/api/v1/productos/**"
                        ).permitAll()

                        // ENDPOINTS DE LOGIN Y REGISTRO
                        .requestMatchers(
                                "/api/v1/usuarios/login",
                                "/api/v1/usuarios"
                        ).permitAll()

                        // 🛑 REGLAS DE AUTORIZACIÓN BASADA EN ROLES

                        // 1. PRODUCTOS (CATÁLOGO)
                        // Permitir a CUALQUIERA (permitAll()) hacer un GET para ver el catálogo.
                        .requestMatchers(HttpMethod.GET, "/api/v1/productos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/compras/**").permitAll()

                        // Restringir la creación, modificación y eliminación solo al ADMIN.
                        .requestMatchers(HttpMethod.POST, "/api/v1/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/productos/**").hasRole("ADMIN")
                        // 2. OTRAS REGLAS...
                        // Ejemplo: Solo ADMIN puede eliminar usuarios
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/usuarios/**").hasRole("ADMIN")

                        // Las compras requieren que el usuario esté autenticado (ya sea ADMIN o USER)
                        .requestMatchers("/api/v1/compras/**").authenticated()

                        // TODO LO DEMÁS NECESITA TOKEN
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
