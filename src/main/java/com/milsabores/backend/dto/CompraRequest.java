package com.milsabores.backend.dto;

import lombok.Data;

import java.util.List;

import lombok.Data;
import java.util.List;

@Data
public class CompraRequest {
    private Long usuarioId;
    private List<DetalleRequest> detalles;

    @Data
    public static class DetalleRequest {
        private Long productoId;
        private Integer cantidad;
        private Double precioUnitario;
    }
}