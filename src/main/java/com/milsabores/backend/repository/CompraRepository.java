package com.milsabores.backend.repository;

import com.milsabores.backend.model.Compra;
import com.milsabores.backend.model.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<DetalleCompra> getDetallesById(Long compra);
}
