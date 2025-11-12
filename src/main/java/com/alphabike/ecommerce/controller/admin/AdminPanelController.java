package com.alphabike.ecommerce.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')") // Asegura que solo los ADMINS puedan acceder
public class AdminPanelController {

    @GetMapping("/panel") // Mapea a /admin/panel
    public String showPanel(Model model) {
        // Variables requeridas por el Layout Maestro
        model.addAttribute("pageTitle", "Panel de Control Principal");
        model.addAttribute("activeMenu", "dashboard"); // Usaremos 'dashboard' para resaltar el link de inicio
        
        // Retorna la vista: src/main/resources/templates/admin/panel.html
        return "admin/panel"; 
    }
}