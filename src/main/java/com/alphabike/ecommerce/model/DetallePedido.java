// src/main/java/com/alphabike/ecommerce/model/DetallePedido.java
package com.alphabike.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "detalles")
@Data
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre; // Nombre del producto al momento de la compra
    private Double precio; 
    private Integer cantidad;
    private Double total;

    // Relaciones
    @ManyToOne
    private Pedido pedido; // Muchos detalles pertenecen a un solo pedido
    
    @ManyToOne
    private Producto producto; // Detalle apunta al producto vendido
}