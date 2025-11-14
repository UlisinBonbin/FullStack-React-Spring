package com.milsabores.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    // ============================================
    // 🔥 IGNORAR LOGIN, REGISTRO Y SWAGGER
    // ============================================
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getServletPath();

        return path.equals("/api/v1/usuarios/login") ||
                path.equals("/api/v1/usuarios") || // registro
                path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs")||
                request.getMethod().equals("OPTIONS");
    }

    // ============================================
    // 🔥 FILTRO JWT NORMAL
    // ============================================
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            // Validar token
            if (!jwtService.validarToken(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                return;
            }

            // Obtener correo desde token
            String correo = jwtService.getCorreoDesdeToken(token);
            String rol = jwtService.getRolDesdeToken(token);

            // Crear Authentication (sin roles por ahora)
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            correo,
                            null,
                            Collections.singleton(new SimpleGrantedAuthority("ROLE_" + rol))
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
