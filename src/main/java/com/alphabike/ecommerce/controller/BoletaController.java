package com.alphabike.ecommerce.controller;

import com.alphabike.ecommerce.model.Pedido;
import com.alphabike.ecommerce.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
@RequestMapping("/pedidos")
public class BoletaController {

    private final PedidoService pedidoService;

    public BoletaController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/boleta/{id}")
    public String showBoleta(@PathVariable Integer id, Model model, RedirectAttributes attributes) {
        Optional<Pedido> pedidoOpt = pedidoService.get(id);

        if (pedidoOpt.isPresent()) {
            model.addAttribute("pedido", pedidoOpt.get());
            return "boleta"; // Mapea a src/main/resources/templates/boleta.html
        }
        attributes.addFlashAttribute("error", "Boleta no encontrada.");
        return "redirect:/";
    }
}