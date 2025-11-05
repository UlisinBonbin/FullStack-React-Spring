package com.milsabores.backend.service;


import com.milsabores.backend.model.Compra;
import com.milsabores.backend.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraService {

    @Autowired
    CompraRepository compraRepository;

    public List<Compra> getAllCompras() {
        return compraRepository.findAll();
    }

    public Compra getCompraById(Long id) {
        return compraRepository.findById(id).orElse(null);
    }

    public Compra saveCompra(Compra compra) {
        return compraRepository.save(compra);
    }

    public Compra updateCompra (Long id, Compra compraActualizada){
        return compraRepository.findById(id)
                .map(compra -> {
                    compra.setUsuario(compraActualizada.getUsuario());
                    compra.setFechaCompra(compraActualizada.getFechaCompra());
                    return compraRepository.save(compra);
                })
                .orElse(null);
    }

    public void deleteCompra(Long id) {
        compraRepository.deleteById(id);
    }

}
