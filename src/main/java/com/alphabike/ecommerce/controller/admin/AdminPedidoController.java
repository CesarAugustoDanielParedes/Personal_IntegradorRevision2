package com.alphabike.ecommerce.controller.admin;

import com.alphabike.ecommerce.model.Pedido;
import com.alphabike.ecommerce.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/pedidos")
public class AdminPedidoController {

    private final PedidoService pedidoService;

    public AdminPedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // 1. LISTAR PEDIDOS (READ)
    @GetMapping("")
    public String home(Model model) {
        List<Pedido> pedidos = pedidoService.findAll();
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("pageTitle", "Gestión de Pedidos");
        model.addAttribute("activeMenu", "pedidos"); 
        return "admin/pedidos/home";
    }
    
    // 2. VER DETALLES DE UN PEDIDO
    @GetMapping("/view/{id}")
    public String view(@PathVariable Integer id, Model model) {
        Optional<Pedido> pedidoOpt = pedidoService.get(id);
        
        if (pedidoOpt.isPresent()) {
             model.addAttribute("pedido", pedidoOpt.get());
             model.addAttribute("pageTitle", "Detalle de Pedido #" + pedidoOpt.get().getNumero());
             model.addAttribute("activeMenu", "pedidos");
             return "admin/pedidos/view";
        }
        return "redirect:/admin/pedidos";
    }

    // 3. ELIMINAR PEDIDO
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        pedidoService.delete(id);
        return "redirect:/admin/pedidos";
    }
}