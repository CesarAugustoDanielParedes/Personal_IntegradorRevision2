// src/main/java/com/alphabike/ecommerce/model/Pedido.java
package com.alphabike.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime fechaCreacion;
    private String numero; // Número de boleta/orden
    private Double total;
    
    // Relaciones
    @ManyToOne // Muchos pedidos pueden pertenecer a un cliente
    private Cliente cliente;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL) // Un pedido tiene muchos detalles
    private List<DetallePedido> detalles;
}