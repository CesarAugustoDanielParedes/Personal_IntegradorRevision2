package com.alphabike.ecommerce.controller.admin;

import com.alphabike.ecommerce.model.Categoria;
import com.alphabike.ecommerce.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categorias")
public class AdminCategoriaController {

    private final CategoriaService categoriaService;

    public AdminCategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // 1. LISTAR CATEGORÍAS (READ)
    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("categorias", categoriaService.findAll());
        // Variables para el Layout Maestro
        model.addAttribute("pageTitle", "Gestión de Categorías");
        model.addAttribute("activeMenu", "categorias"); 
        return "admin/categorias/home"; 
    }
    
    // 2. MOSTRAR FORMULARIO DE CREACIÓN
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("categoria", new Categoria());
        model.addAttribute("pageTitle", "Crear Categoría");
        model.addAttribute("activeMenu", "categorias");
        return "admin/categorias/create";
    }

    // 3. GUARDAR CATEGORÍA
    @PostMapping("/save")
    public String save(@ModelAttribute Categoria categoria) {
        categoriaService.save(categoria);
        return "redirect:/admin/categorias";
    }

    // 4. MOSTRAR FORMULARIO DE EDICIÓN
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Categoria categoria = categoriaService.get(id).orElse(null);
        if (categoria != null) {
             model.addAttribute("categoria", categoria);
             model.addAttribute("pageTitle", "Editar Categoría");
             model.addAttribute("activeMenu", "categorias");
             return "admin/categorias/edit";
        }
        return "redirect:/admin/categorias";
    }

    // 5. ELIMINAR CATEGORÍA
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        categoriaService.delete(id);
        return "redirect:/admin/categorias";
    }
}