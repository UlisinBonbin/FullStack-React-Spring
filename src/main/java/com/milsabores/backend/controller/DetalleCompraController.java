package com.milsabores.backend.controller;

import com.milsabores.backend.model.DetalleCompra;
import com.milsabores.backend.repository.DetalleCompraRepository;
import com.milsabores.backend.service.DetalleCompraService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/detalle-compra")
public class DetalleCompraController {
    @Autowired
    private DetalleCompraService detalleCompraService;

    @GetMapping
    @Operation(summary = "lista de todas las compras")
    public List<DetalleCompra> getAllDetalleCompra(){
        return detalleCompraService.getAllDetalleCompra();
    }

    @GetMapping("/compra/{id}")
    @Operation(summary = "Obtener los detalles de una compra por su id")
    public List<DetalleCompra> getDetallesByCompraId(@PathVariable Long id) {
        return detalleCompraService.getDetallesByCompraId(id);
    }
}
