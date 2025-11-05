package com.milsabores.backend.service;

import com.milsabores.backend.model.DetalleCompra;
import com.milsabores.backend.repository.DetalleCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleCompraService {
    @Autowired
    DetalleCompraRepository detalleCompraRepository;

    public List<DetalleCompra> getDetallesByCompraId(Long compraId) {
        return detalleCompraRepository.findByCompra_Id(compraId);
    }
}
