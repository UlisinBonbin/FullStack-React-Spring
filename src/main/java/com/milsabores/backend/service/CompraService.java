package com.milsabores.backend.service;

import com.milsabores.backend.dto.CompraRequest;
import com.milsabores.backend.model.Compra;
import com.milsabores.backend.model.DetalleCompra;
import com.milsabores.backend.model.Producto;
import com.milsabores.backend.model.Usuario;
import com.milsabores.backend.repository.CompraRepository;
import com.milsabores.backend.repository.DetalleCompraRepository;
import com.milsabores.backend.repository.ProductoRepository;
import com.milsabores.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DetalleCompraRepository detalleCompraRepository;

    public List<Compra> getAllCompras() {
        return compraRepository.findAll();
    }

    public Compra getCompraById(Long id) {
        return compraRepository.findById(id).orElse(null);
    }

    // ✅ Nuevo método completo
    public Compra registrarCompra(CompraRequest request) {
        // Buscar usuario
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear la compra con la fecha actual
        Compra compra = new Compra();
        compra.setUsuario(usuario);
        compra.setFechaCompra(LocalDate.now().toString());
        compra = compraRepository.save(compra);

        // Crear los detalles de la compra
        List<DetalleCompra> detalles = new ArrayList<>();
        for (CompraRequest.DetalleRequest d : request.getDetalles()) {
            Producto producto = productoRepository.findById(d.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            DetalleCompra detalle = new DetalleCompra();
            detalle.setCompra(compra);
            detalle.setProducto(producto);
            detalle.setCantidad(d.getCantidad());
            detalle.setPrecioUnitario(d.getPrecioUnitario());
            detalles.add(detalle);
        }

        // Guardar todos los detalles
        detalleCompraRepository.saveAll(detalles);
        compra.setDetalles(detalles);

        return compra;
    }

    public Compra updateCompra(Long id, Compra compraActualizada) {
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
