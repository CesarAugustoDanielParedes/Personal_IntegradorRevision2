package com.alphabike.ecommerce.service;

import com.alphabike.ecommerce.model.Producto;
import com.alphabike.ecommerce.model.ItemCarrito; // Nueva importación
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import java.util.*;
import java.util.stream.Collectors;

@Service
@SessionScope
public class CarritoService {

    // Map: Key = Producto ID (Integer), Value = Cantidad (Integer)
    private Map<Integer, Integer> itemsMap = new HashMap<>();
    private final ProductoService productoService;

    // Inyección del ProductoService para obtener la data del producto
    public CarritoService(ProductoService productoService) {
        this.productoService = productoService;
    }

    // --- LÓGICA CRUD del Carrito ---

    // 1. AÑADIR/ACTUALIZAR
    public void addProducto(Integer productoId, int cantidad) {
        itemsMap.put(productoId, itemsMap.getOrDefault(productoId, 0) + cantidad);
    }
    
    // 2. ELIMINAR ÍTEM COMPLETO
    public void removeProducto(Integer productoId) {
        itemsMap.remove(productoId);
    }
    
    // 3. OBTENER ÍTEMS COMPLETOS (Producto + Cantidad)
    public List<ItemCarrito> getItems() {
        // Mapea los IDs del carrito a objetos ItemCarrito
        return itemsMap.entrySet().stream()
            .map(entry -> {
                Optional<Producto> productoOpt = productoService.get(entry.getKey());
                // Si el producto existe, crea el ItemCarrito
                if (productoOpt.isPresent()) {
                    return new ItemCarrito(productoOpt.get(), entry.getValue());
                }
                return null; // Ignorar si el producto no existe (debería ser raro)
            })
            .filter(Objects::nonNull) // Elimina los nulos
            .collect(Collectors.toList());
    }

    // 4. CALCULAR TOTAL DEL CARRITO
    public Double getTotal() {
        return getItems().stream()
            .mapToDouble(ItemCarrito::getSubtotal)
            .sum();
    }
    
    // 5. VACIAR CARRITO (después de finalizar la compra)
    public void clear() {
        itemsMap.clear();
    }
}