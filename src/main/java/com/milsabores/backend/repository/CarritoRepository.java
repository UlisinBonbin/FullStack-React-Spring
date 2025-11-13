package com.milsabores.backend.repository;

import com.milsabores.backend.model.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarritoRepository extends JpaRepository<CarritoItem, Long> {
    // Busca todos los ítems de un usuario específico
    List<CarritoItem> findByUsuarioId(Long usuarioId);

    // Busca un ítem específico por usuario y producto (clave para la lógica de "actualizar cantidad")
    Optional<CarritoItem> findByUsuarioIdAndProductoId(Long usuarioId, Long productoId);

    // Borra todos los ítems de un usuario (clave para la lógica de "limpiar carrito después de comprar")
    void deleteByUsuarioId(Long usuarioId);
}
