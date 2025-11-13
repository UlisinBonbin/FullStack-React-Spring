package com.milsabores.backend.service;

import com.milsabores.backend.dto.CompraRequest;
import com.milsabores.backend.model.*;
import com.milsabores.backend.repository.*;
import jakarta.transaction.Transactional;
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

    @Autowired
    private CarritoRepository carritoRepository;

    public List<Compra> getAllCompras() {
        return compraRepository.findAll();
    }

    public Compra getCompraById(Long id) {
        return compraRepository.findById(id).orElse(null);
    }

    @Transactional // Anotación clave para que toda la operación sea atómica.
    public Compra registrarCompra(CompraRequest request) {
        // A. BUSCAR EL USUARIO
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + request.getUsuarioId()));

        // B. OBTENER LOS ÍTEMS DEL CARRITO DESDE LA BASE DE DATOS (¡EL GRAN CAMBIO!)
        List<CarritoItem> itemsDelCarrito = carritoRepository.findByUsuarioId(usuario.getId());
        if (itemsDelCarrito.isEmpty()) {
            throw new RuntimeException("El carrito está vacío. No se puede registrar la compra.");
        }

        // C. CREAR LA COMPRA PRINCIPAL Y GUARDARLA PARA OBTENER UN ID
        Compra nuevaCompra = new Compra();
        nuevaCompra.setUsuario(usuario);
        nuevaCompra.setFechaCompra(LocalDate.now().toString()); // Usamos LocalDate directamente
        nuevaCompra.setDetalles(new ArrayList<>()); // Inicializamos la lista

        // Guardamos la compra para que Hibernate le asigne un ID
        Compra compraGuardada = compraRepository.save(nuevaCompra);

        // D. TRANSFORMAR LOS ÍTEMS DEL CARRITO EN DETALLES DE COMPRA
        for (CarritoItem itemCarrito : itemsDelCarrito) {
            DetalleCompra detalle = new DetalleCompra();

            // ¡LA ASOCIACIÓN CLAVE!
            detalle.setCompra(compraGuardada);

            detalle.setProducto(itemCarrito.getProducto());
            detalle.setCantidad(itemCarrito.getCantidad());
            // Guardamos el precio del producto en el momento de la compra
            detalle.setPrecioUnitario(itemCarrito.getProducto().getPrecio());

            // Añadimos el detalle a la lista de la compra principal
            compraGuardada.getDetalles().add(detalle);
        }

        // E. VACIAR EL CARRITO DEL USUARIO
        // Como el usuario ya compró, limpiamos su carrito.
        carritoRepository.deleteByUsuarioId(usuario.getId());

        // F. DEVOLVER LA COMPRA COMPLETA
        // Gracias a @Transactional y CascadeType.ALL en la entidad Compra,
        // los detalles se guardan automáticamente al final de la transacción.
        return compraGuardada;
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
