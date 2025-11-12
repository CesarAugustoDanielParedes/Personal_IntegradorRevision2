package com.alphabike.ecommerce.controller.admin;

import com.alphabike.ecommerce.model.Cliente;
import com.alphabike.ecommerce.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/admin/clientes")
public class AdminClienteController {

    private final ClienteService clienteService;

    public AdminClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // 1. LISTAR CLIENTES (READ)
    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("pageTitle", "Gestión de Clientes");
        model.addAttribute("activeMenu", "clientes"); 
        return "admin/clientes/home";
    }
    
    // 2. MOSTRAR FORMULARIO DE EDICIÓN
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Cliente> clienteOpt = clienteService.get(id);
        
        if (clienteOpt.isPresent()) {
             model.addAttribute("cliente", clienteOpt.get());
             model.addAttribute("pageTitle", "Ver/Editar Cliente");
             model.addAttribute("activeMenu", "clientes");
             return "admin/clientes/edit";
        }
        return "redirect:/admin/clientes";
    }

    // 3. GUARDAR CAMBIOS DEL CLIENTE
    @PostMapping("/save")
    public String save(@ModelAttribute Cliente cliente) {
        clienteService.save(cliente);
        return "redirect:/admin/clientes";
    }

    // 4. ELIMINAR CLIENTE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        clienteService.delete(id);
        return "redirect:/admin/clientes";
    }
}