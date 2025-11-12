package com.alphabike.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EcommerceApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(EcommerceApplication.class, args);
        
        // --- CÓDIGO TEMPORAL PARA GENERAR HASH ---
        
        // Obtener el bean de BCryptPasswordEncoder
        BCryptPasswordEncoder passwordEncoder = context.getBean(BCryptPasswordEncoder.class);
        
        // Define la contraseña que quieres usar (Ej: "mipassadmin")
        String rawPassword = "mipassadmin"; 
        
        // Genera el hash (cifrado)
        String hashedPassword = passwordEncoder.encode(rawPassword);
        
        System.out.println("---------------------------------------------");
        System.out.println("Contraseña Plana: " + rawPassword);
        System.out.println("Contraseña Cifrada (HASH): " + hashedPassword);
        System.out.println("---------------------------------------------");
        
        // --- FIN DEL CÓDIGO TEMPORAL ---
    }
}