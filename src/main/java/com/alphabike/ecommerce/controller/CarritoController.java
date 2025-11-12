// src/main/java/com/alphabike/ecommerce/controller/CarritoController.java
package com.alphabike.ecommerce.controller;

import com.alphabike.ecommerce.model.Cliente;
import com.alphabike.ecommerce.model.Pedido;
import com.alphabike.ecommerce.service.CarritoService;
import com.alphabike.ecommerce.service.ProductoService;
import com.alphabike.ecommerce.service.PedidoService; // Nueva importación
import com.alphabike.ecommerce.service.ClienteService; // Nueva importación
import org.springframework.security.core.Authentication; // Para obtener el usuario logueado
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    private final CarritoService carritoService;
    private final ProductoService productoService;
    private final PedidoService pedidoService; // Nuevo
    private final ClienteService clienteService; // Nuevo

    public CarritoController(CarritoService carritoService, ProductoService productoService, PedidoService pedidoService, ClienteService clienteService) {
        this.carritoService = carritoService;
        this.productoService = productoService;
        this.pedidoService = pedidoService;
        this.clienteService = clienteService;
    }

    // ... (Métodos add, show, delete se mantienen igual) ...

    // 4. PASAR A PAGO (Checkout)
    @GetMapping("/checkout")
    public String checkout(Authentication authentication, Model model, RedirectAttributes attributes) {
        // 1. Verificar si hay productos en el carrito
        if (carritoService.getItems().isEmpty()) {
            attributes.addFlashAttribute("error", "El carrito está vacío.");
            return "redirect:/carrito";
        }
        
        // 2. Verificar si el cliente está logueado
        if (authentication == null || !authentication.isAuthenticated() || authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            attributes.addFlashAttribute("info", "Debes iniciar sesión para proceder al pago.");
            return "redirect:/login";
        }
        
        // Obtener la información del cliente logueado
        String email = authentication.getName();
        Cliente cliente = clienteService.findByEmail(email);

        model.addAttribute("cliente", cliente);
        model.addAttribute("items", carritoService.getItems());
        model.addAttribute("total", carritoService.getTotal());
        
        return "checkout"; // Mapea a src/main/resources/templates/checkout.html
    }

    // 5. CONFIRMAR PAGO Y GENERAR BOLETA
    @PostMapping("/confirmar-pago")
    public String confirmarPago(@RequestParam String metodoPago, 
                                Authentication authentication, 
                                RedirectAttributes attributes) {
                                    
        // 1. Obtener cliente y verificar login
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        String email = authentication.getName();
        Cliente cliente = clienteService.findByEmail(email);
        
        if (cliente == null || carritoService.getItems().isEmpty()) {
            attributes.addFlashAttribute("error", "Error en la sesión o carrito vacío.");
            return "redirect:/carrito";
        }

        // 2. SIMULACIÓN DE PAGO
        // En un sistema real, aquí iría la llamada a la API de tarjeta/Yape.
        boolean pagoExitoso = true; // SIMULACIÓN: Asumimos éxito

        if (pagoExitoso) {
            // 3. Crear el Pedido (Boleta) en la BD
            Pedido pedidoGenerado = pedidoService.crearPedidoDesdeCarrito(
                cliente, 
                carritoService.getItems(), 
                carritoService.getTotal()
            );

            // 4. Vaciar el Carrito de la Sesión
            carritoService.clear();

            attributes.addFlashAttribute("success", "¡Pago exitoso! Se generó la boleta.");
            // Redirigir a la vista de la boleta
            return "redirect:/pedidos/boleta/" + pedidoGenerado.getId();
            
        } else {
            attributes.addFlashAttribute("error", "El pago fue rechazado. Intente con otro método.");
            return "redirect:/carrito/checkout";
        }
    }
}