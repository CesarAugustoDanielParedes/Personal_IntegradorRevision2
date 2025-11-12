package com.alphabike.ecommerce.controller;

import com.alphabike.ecommerce.model.Cliente;
import com.alphabike.ecommerce.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    private final ClienteService clienteService;

    public RegistroController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // 1. MOSTRAR FORMULARIO
    @GetMapping("")
    public String showRegistrationForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "registro"; // Mapea a src/main/resources/templates/registro.html
    }

    // 2. PROCESAR REGISTRO
    @PostMapping("/procesar")
    public String processRegistration(@ModelAttribute Cliente cliente, RedirectAttributes attributes) {
        
        // --- Validación de Correo (Simple) ---
        // Verificar si el email ya existe
        if (clienteService.findByEmail(cliente.getEmail()) != null) {
            attributes.addFlashAttribute("error", "El correo electrónico ya está registrado.");
            // Si hay error, redirige de nuevo al formulario
            return "redirect:/registro"; 
        }

        // --- Guardar Cliente (la contraseña se cifra dentro del servicio) ---
        clienteService.save(cliente);

        // Mensaje de éxito y redirección al login
        attributes.addFlashAttribute("success", "Registro exitoso. ¡Inicia sesión!");
        return "redirect:/login"; 
    }
}