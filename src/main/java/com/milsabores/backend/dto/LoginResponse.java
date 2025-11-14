package com.milsabores.backend.dto;

import com.milsabores.backend.model.Usuario;
import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private Usuario usuario;

    public LoginResponse(String token, Usuario usuario) {
        this.token = token;
        this.usuario = usuario;
    }
}
