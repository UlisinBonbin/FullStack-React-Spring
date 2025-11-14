package com.milsabores.backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final Key secretKey = Keys.hmacShaKeyFor("MI_CLAVE_SUPER_SECRETA_QUE_DEBE_SER_MAS_LARGA123".getBytes());

    // Crear token
    public String generarToken(String correo, String rol) {
        return Jwts.builder()
                .setSubject(correo)
                .claim("role", rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2)) // 2 horas
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Obtener correo desde token
    public String getCorreoDesdeToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validar token
    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
