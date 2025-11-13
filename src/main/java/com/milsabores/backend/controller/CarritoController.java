package com.milsabores.backend.controller;

import com.milsabores.backend.model.CarritoItem;
import com.milsabores.backend.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carrito")

public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/{usuarioId}") //El id del usuario
    @Operation(summary = "Obtener el carrito de un usuario por su ID")
    public ResponseEntity<List<CarritoItem>> getCarritoByUsuarioId(@PathVariable Long usuarioId) {
        List<CarritoItem> items = carritoService.getItemsByUsuarioId(usuarioId);
        return ResponseEntity.ok(items);
    }

    @PostMapping
    @Operation(summary = "Agregar un ítem al carrito o actualizar su cantidad si ya existe")
    public ResponseEntity<CarritoItem> agregarItem(@RequestBody CarritoItem item) {
        CarritoItem itemGuardado = carritoService.agregarOActualizarItem(item);
        return new ResponseEntity<>(itemGuardado, HttpStatus.CREATED);
    }

    @DeleteMapping("/{itemId}")
    @Operation(summary = "Eliminar un ítem del carrito por su ID")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long itemId) {
        carritoService.eliminarItem(itemId);
        return ResponseEntity.noContent().build(); // Delvolverá un 204, osea que no hay contenido
    }

    @DeleteMapping("/usuario/{usuarioId}")
    @Operation(summary = "Eliminar todos los ítems del carrito de un usuario")
    public ResponseEntity<Void> limpiarCarritoDelUsuario(@PathVariable Long usuarioId) {
        carritoService.limpiarCarritoDelUsuario(usuarioId);
        return ResponseEntity.noContent().build(); // Devuelve 204, indicando que la operación fue exitosa pero no hay nada que devolver.
    }

}
