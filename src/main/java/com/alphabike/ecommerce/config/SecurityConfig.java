package com.alphabike.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Habilita anotaciones de seguridad como @PreAuthorize
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    // Inyectamos nuestro UserDetailsService (que carga usuarios desde ClienteService)
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // 1. Define el Codificador de Contraseñas (BCrypt)
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Define el proveedor de autenticación
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); 
        authProvider.setPasswordEncoder(passwordEncoder()); 
        return authProvider;
    }

    // 3. Define la cadena de filtros de seguridad (Reglas de acceso)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        http
            
            // 1. Configuración de Autorización (quién puede acceder a dónde)
            .authorizeHttpRequests(authorize -> authorize
                // RUTAS PÚBLICAS Y ESTÁTICAS (Permitidas a todos)
                .requestMatchers(
                    // Archivos Estáticos (CSS, JS, Imágenes, Medios)
                    "/css/**", "/js/**", "/images/**", "/media/**", 
                    
                    // Rutas Públicas de la Tienda
                    "/", "/productos", "/productos/**", "/contacto", "/login",
                    
                    // Rutas de Registro y Carrito (Core E-commerce)
                    "/registro", "/registro/procesar", "/carrito/**", 
                    "/checkout", "/pedidos/boleta/**" 
                ).permitAll()
                
                // RUTAS ADMINISTRATIVAS (Protegidas por rol)
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                // Cualquier otra ruta requiere autenticación (ej. /perfil, /logout)
                .anyRequest().authenticated()
            )
            // 2. Configuración del Formulario de Login
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
                // REDIRECCIÓN FINAL: Envía al administrador al panel centralizado
                .defaultSuccessUrl("/admin/panel", true) 
                .failureUrl("/login?error=true")
            )
            // 3. Configuración del Logout
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
                .logoutSuccessUrl("/") 
            );

        return http.build();
    }
}