// src/main/java/com/alphabike/ecommerce/model/Cliente.java
package com.alphabike.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "clientes")
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String apellido;
    private String email; // Aquí se aplica la validación de correo electrónico
    private String password; // Debe ser cifrada (usando Spring Security)
    private String direccion;
    private String telefono;
    
    // Relación opcional: El cliente puede tener varios pedidos
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos; 
}