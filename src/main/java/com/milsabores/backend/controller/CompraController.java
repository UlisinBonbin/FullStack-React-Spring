package com.milsabores.backend.controller;

import com.milsabores.backend.model.Compra;
import com.milsabores.backend.service.CompraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Operation(summary =  "obtener una compra por su id")
    public Compra getCompraById(@PathVariable Long id){
        return compraService.getCompraById(id);
    }

    @PostMapping
    @Operation(summary =  "añadir una nueva compra")
    public Compra saveCompra(@RequestBody Compra compra){
        return compraService.saveCompra(compra);
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
