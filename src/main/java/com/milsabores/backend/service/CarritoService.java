package com.milsabores.backend.service;

import com.milsabores.backend.exception.ResourceNotFoundException;
import com.milsabores.backend.model.CarritoItem;
import com.milsabores.backend.model.Producto;
import com.milsabores.backend.model.Usuario;
import com.milsabores.backend.repository.CarritoRepository;
import com.milsabores.backend.repository.ProductoRepository;
import com.milsabores.backend.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {
    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    /**
     * Obtiene todos los ítems del carrito para un usuario específico.
     * @param usuarioId El ID del usuario.
     * @return Una lista de los ítems en su carrito.
     */
    public List<CarritoItem> getItemsByUsuarioId(Long usuarioId) {
        // Busca al usuario para asegurarse de que existe antes de devolver el carrito.
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario con id: " + usuarioId));
        return carritoRepository.findByUsuarioId(usuarioId);
    }

    /**
     * Agrega un nuevo ítem al carrito o actualiza la cantidad si el producto ya existe.
     * Es transaccional para asegurar la integridad de los datos.
     * @param nuevoItem El objeto CarritoItem con usuarioId, productoId y cantidad.
     * @return El ítem del carrito guardado o actualizado.
     */
    @Transactional
    public CarritoItem agregarOActualizarItem(CarritoItem nuevoItem) {
        // 1. Validar que el usuario y el producto existen.
        Usuario usuario = usuarioRepository.findById(nuevoItem.getUsuario().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(nuevoItem.getProducto().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        // 2. Comprobar si el producto ya está en el carrito de ese usuario.
        Optional<CarritoItem> itemExistenteOpt = carritoRepository.findByUsuarioIdAndProductoId(usuario.getId(), producto.getId());

        if (itemExistenteOpt.isPresent()) {
            // Si el ítem ya existe, actualizamos la cantidad.
            CarritoItem itemExistente = itemExistenteOpt.get();
            int nuevaCantidad = itemExistente.getCantidad() + nuevoItem.getCantidad();
            itemExistente.setCantidad(nuevaCantidad);
            return carritoRepository.save(itemExistente);
        } else {
            // Si es un ítem nuevo, lo asociamos y lo guardamos.
            nuevoItem.setUsuario(usuario);
            nuevoItem.setProducto(producto);
            return carritoRepository.save(nuevoItem);
        }
    }

    /**
     * Elimina un ítem del carrito por su ID.
     * @param itemId El ID del ítem en la tabla CarritoItem.
     */
    public void eliminarItem(Long itemId) {
        // Comprueba si el ítem existe antes de intentar borrarlo.
        CarritoItem item = carritoRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el ítem del carrito con id: " + itemId));
        carritoRepository.delete(item);
    }

    /**
     * Limpia completamente el carrito de un usuario.
     * Muy útil después de que se completa una compra.
     * @param usuarioId El ID del usuario cuyo carrito se va a limpiar.
     */
    @Transactional
    public void limpiarCarritoDelUsuario(Long usuarioId) {
        // Verificar que el usuario exista
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario con id: " + usuarioId));

        carritoRepository.deleteByUsuarioId(usuarioId);
    }
}
