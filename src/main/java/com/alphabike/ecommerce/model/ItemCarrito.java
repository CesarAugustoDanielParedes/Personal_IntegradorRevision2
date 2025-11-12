package com.alphabike.ecommerce.model;

import lombok.Data;

@Data // Genera getters, setters, etc.
public class ItemCarrito {
    
    private Producto producto;
    private Integer cantidad;
    
    public ItemCarrito(Producto producto, Integer cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }
    
    // Método para calcular el subtotal de este ítem
    public Double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }
}