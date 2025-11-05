package com.milsabores.backend.controller;

import com.milsabores.backend.model.Compra;
import com.milsabores.backend.service.CompraService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/compras")
@Api(value = "Compra Management System")
public class CompraController {
    @Autowired
    private CompraService compraService;

    @GetMapping
    @ApiOperation(value = "Lista de todas las compras", response = List.class)
    public List<Compra>getAllCompras(){
        return compraService.getAllCompras();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "obtener una compra por su id")
    public Compra getCompraById(@PathVariable Long id){
        return compraService.getCompraById(id);
    }

    @PostMapping
    @ApiOperation(value = "añadir una nueva compra")
    public Compra saveCompra(@RequestBody Compra compra){
        return compraService.saveCompra(compra);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "actualizar una compra")
    public Compra updateCompra(@PathVariable Long id, @RequestBody Compra compra){
        return compraService.updateCompra(id, compra);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "eliminar una compra")
    public void deleteCompra(@PathVariable Long id){
        compraService.deleteCompra(id);
    }

}
