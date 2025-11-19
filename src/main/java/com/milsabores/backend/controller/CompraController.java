package com.milsabores.backend.controller;

import com.milsabores.backend.dto.CompraRequest;
import com.milsabores.backend.model.Compra;
import com.milsabores.backend.service.CompraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/compras")
@Tag(name = "Compra Management System")
public class CompraController {
    @Autowired
    private CompraService compraService;

    @GetMapping
    @Operation(summary = "Lista de todas las compras")
    public List<Compra>getAllCompras(){
        return compraService.getAllCompras();
    }

    @GetMapping("/{id}")
    @Operation(summary =  "Obtener una compra por su id")
    public Compra getCompraById(@PathVariable Long id){
        return compraService.getCompraById(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener la compra por el id del usuario")
    public ResponseEntity<List<Compra>>findByUsuario_Id(@PathVariable Long usuarioId){
        List<Compra> compraPorUsuario = compraService.findByUsuario_Id(usuarioId);
        return ResponseEntity.ok(compraPorUsuario);
    }

    @PostMapping
    @Operation(summary = "Registrar una nueva compra con usuario y detalles")
    public Compra registrarCompra(@RequestBody CompraRequest request) {
        return compraService.registrarCompra(request);
    }

    @PutMapping("/{id}")
    @Operation(summary =  "actualizar una compra")
    public Compra updateCompra(@PathVariable Long id, @RequestBody Compra compra){
        return compraService.updateCompra(id, compra);
    }

    @DeleteMapping("/{id}")
    @Operation(summary =  "eliminar una compra")
    public void deleteCompra(@PathVariable Long id){
        compraService.deleteCompra(id);
    }

}
