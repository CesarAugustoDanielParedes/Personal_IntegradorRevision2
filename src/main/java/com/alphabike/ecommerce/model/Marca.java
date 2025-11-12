// src/main/java/com/alphabike/ecommerce/model/Marca.java
package com.alphabike.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "marcas")
@Data
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
}