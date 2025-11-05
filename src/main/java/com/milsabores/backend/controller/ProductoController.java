package com.milsabores.backend.controller;

import com.milsabores.backend.model.Producto;
import com.milsabores.backend.model.Usuario;
import com.milsabores.backend.service.ProductoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@Api(value = "Products management System")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    @ApiOperation(value = "Lista de productos", response = List.class)
    public List<Producto>getAllProductos(){return productoService.getAllProductos();}

    @GetMapping("/{id}")
    @ApiOperation(value = "Obtener producto por su id")
    public Producto getProductoById(@PathVariable Long id){
        return productoService.getProductoById(id);
    }

    @PostMapping
    @ApiOperation(value = "Añadir un nuevo producto")
    public  Producto saveProducto(@RequestBody Producto producto){
        return productoService.saveProducto(producto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "actualizar un producto ya exisitente")
    public Producto updateProducto(@PathVariable Long id, @RequestBody Producto producto){
        return productoService.updateProducto(id, producto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "eliminar un producto")
    public void deleteProducto(@PathVariable Long id){
        productoService.deleteProducto(id);
    }
}
