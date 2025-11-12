// src/main/java/com/alphabike/ecommerce/model/Banner.java
package com.alphabike.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "banners")
@Data
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String urlImagen; // Ruta o nombre del archivo de la imagen
    private String enlaceDestino; // URL a donde redirige el banner
    private Integer orden;
}