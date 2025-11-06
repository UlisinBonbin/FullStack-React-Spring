package com.milsabores.backend.controller;

import com.milsabores.backend.model.Producto;
import com.milsabores.backend.model.Usuario;
import com.milsabores.backend.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Products Management System")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    @Operation(summary = "Lista de productos")
    public List<Producto>getAllProductos(){return productoService.getAllProductos();}

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por su id")
    public Producto getProductoById(@PathVariable Long id){
        return productoService.getProductoById(id);
    }

    @PostMapping
    @Operation(summary = "Añadir un nuevo producto")
    public  Producto saveProducto(@RequestBody Producto producto){
        return productoService.saveProducto(producto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "actualizar un producto ya exisitente")
    public Producto updateProducto(@PathVariable Long id, @RequestBody Producto producto){
        return productoService.updateProducto(id, producto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "eliminar un producto")
    public void deleteProducto(@PathVariable Long id){
        productoService.deleteProducto(id);
    }
}
