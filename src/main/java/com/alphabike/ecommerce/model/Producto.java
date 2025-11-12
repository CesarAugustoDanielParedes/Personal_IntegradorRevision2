// src/main/java/com/alphabike/ecommerce/model/Producto.java
package com.alphabike.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data; 

@Entity 
@Table(name = "productos")
@Data 
public class Producto {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String imagen;
    private Integer stock;

    // --- Nuevas Relaciones ---
    
    @ManyToOne // Muchos productos pertenecen a una Marca
    @JoinColumn(name = "marca_id") // Columna de clave foránea en la tabla productos
    private Marca marca; 
    
    @ManyToOne // Muchos productos pertenecen a una Categoria
    @JoinColumn(name = "categoria_id") // Columna de clave foránea en la tabla productos
    private Categoria categoria;
}