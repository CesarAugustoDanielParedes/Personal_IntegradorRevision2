package com.alphabike.ecommerce.controller.admin;

import com.alphabike.ecommerce.model.Marca;
import com.alphabike.ecommerce.service.MarcaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/marcas") 
public class AdminMarcaController {

    private final MarcaService marcaService;

    public AdminMarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    // 1. LISTAR MARCAS (READ)
    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("marcas", marcaService.findAll());
        // VARIABLES PARA EL LAYOUT MAESTRO
        model.addAttribute("pageTitle", "Gestión de Marcas");
        model.addAttribute("activeMenu", "marcas"); // <-- Activa el enlace en el sidebar
        return "admin/marcas/home";
    }
    
    // 2. MOSTRAR FORMULARIO DE CREACIÓN
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("marca", new Marca());
        model.addAttribute("pageTitle", "Crear Marca");
        model.addAttribute("activeMenu", "marcas");
        return "admin/marcas/create"; 
    }

    // 3. GUARDAR MARCA
    @PostMapping("/save")
    public String save(@ModelAttribute Marca marca) {
        marcaService.save(marca);
        return "redirect:/admin/marcas"; 
    }

    // 4. MOSTRAR FORMULARIO DE EDICIÓN
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Marca marca = marcaService.get(id).orElse(null);
        if (marca != null) {
             model.addAttribute("marca", marca);
             model.addAttribute("pageTitle", "Editar Marca");
             model.addAttribute("activeMenu", "marcas");
             return "admin/marcas/edit";
        }
        return "redirect:/admin/marcas";
    }

    // 5. ELIMINAR MARCA
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        marcaService.delete(id);
        return "redirect:/admin/marcas";
    }
}