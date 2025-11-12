package com.alphabike.ecommerce.service;

import com.alphabike.ecommerce.model.Cliente;
import com.alphabike.ecommerce.model.Pedido;
import com.alphabike.ecommerce.model.DetallePedido; 
import com.alphabike.ecommerce.model.ItemCarrito; 
import com.alphabike.ecommerce.repository.PedidoRepository;
import com.alphabike.ecommerce.repository.DetallePedidoRepository; 
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository, DetallePedidoRepository detallePedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.detallePedidoRepository = detallePedidoRepository;
    }

    // Método principal para crear un pedido desde el carrito
    public Pedido crearPedidoDesdeCarrito(Cliente cliente, List<ItemCarrito> items, Double total) {
        // 1. Crear la cabecera del Pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFechaCreacion(LocalDateTime.now());
        pedido.setNumero(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        pedido.setTotal(total);

        // 2. Crear los DetallePedido a partir de los ItemCarrito
        List<DetallePedido> detalles = items.stream().map(item -> {
            DetallePedido detalle = new DetallePedido();
            
            // --- CÓDIGO CORREGIDO: USANDO GETTERS EXPLÍCITOS ---
            detalle.setNombre(item.getProducto().getNombre());
            detalle.setPrecio(item.getProducto().getPrecio());
            detalle.setCantidad(item.getCantidad());
            detalle.setTotal(item.getSubtotal());
            detalle.setProducto(item.getProducto());
            // --------------------------------------------------
            
            detalle.setPedido(pedido);
            return detalle;
        }).collect(Collectors.toList());

        pedido.setDetalles(detalles);

        // 3. Guardar el Pedido (y los detalles en cascada)
        return pedidoRepository.save(pedido);
    }

    // CRUD Básico (se mantienen sin cambios)
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> get(Integer id) {
        return pedidoRepository.findById(id);
    }
    
    public void delete(Integer id) {
        pedidoRepository.deleteById(id);
    }
}