package com.alphabike.ecommerce.controller.admin;

import com.alphabike.ecommerce.model.Producto;
import com.alphabike.ecommerce.service.ProductoService;
import com.alphabike.ecommerce.service.MarcaService;
import com.alphabike.ecommerce.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
@RequestMapping("/admin/productos")
public class AdminProductoController {

    private final ProductoService productoService;
    private final MarcaService marcaService;
    private final CategoriaService categoriaService;

    // Inyección de dependencias de todos los servicios necesarios
    public AdminProductoController(ProductoService productoService, MarcaService marcaService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.marcaService = marcaService;
        this.categoriaService = categoriaService;
    }

    // 1. LISTAR PRODUCTOS (READ) - ¡ACTUALIZADO PARA EL LAYOUT!
    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("productos", productoService.findAll());
        
        // Variables para el Layout Maestro (admin_layout.html)
        model.addAttribute("pageTitle", "Gestión de Productos");
        model.addAttribute("activeMenu", "productos"); 
        
        return "admin/productos/home"; // Retorna la vista que usará el layout
    }
    
    // 2. MOSTRAR FORMULARIO DE CREACIÓN (CREATE - Paso 1)
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("marcas", marcaService.findAll());
        model.addAttribute("categorias", categoriaService.findAll());
        
        model.addAttribute("pageTitle", "Crear Producto"); // Para el layout
        model.addAttribute("activeMenu", "productos");
        
        return "admin/productos/create";
    }

    // 3. GUARDAR PRODUCTO (CREATE/UPDATE)
    @PostMapping("/save")
    public String save(@ModelAttribute Producto producto, 
                       @RequestParam("file") MultipartFile file) throws IOException { 
        
        productoService.save(producto, file); 
        return "redirect:/admin/productos";
    }

    // 4. MOSTRAR FORMULARIO DE EDICIÓN (UPDATE - Paso 1)
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Producto producto = productoService.get(id).orElse(null);
        
        if (producto != null) {
             model.addAttribute("producto", producto);
             model.addAttribute("marcas", marcaService.findAll());
             model.addAttribute("categorias", categoriaService.findAll());
             
             model.addAttribute("pageTitle", "Editar Producto"); // Para el layout
             model.addAttribute("activeMenu", "productos");
             
             return "admin/productos/edit";
        }
        return "redirect:/admin/productos";
    }

    // 5. ELIMINAR PRODUCTO (DELETE)
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        productoService.delete(id);
        return "redirect:/admin/productos";
    }
}