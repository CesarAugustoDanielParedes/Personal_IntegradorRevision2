package com.alphabike.ecommerce.controller.admin;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alphabike.ecommerce.model.Banner;
import com.alphabike.ecommerce.service.BannerService;

@Controller
@RequestMapping("/admin/banners")
public class AdminBannerController {

    private final BannerService bannerService;

    public AdminBannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    // 1. LISTAR BANNERS (READ)
    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("banners", bannerService.findAll());
        model.addAttribute("pageTitle", "Gestión de Banners");
        model.addAttribute("activeMenu", "banners"); 
        return "admin/banners/home";
    }
    
    // 2. MOSTRAR FORMULARIO DE CREACIÓN
    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("banner", new Banner());
        model.addAttribute("pageTitle", "Crear Banner");
        model.addAttribute("activeMenu", "banners");
        return "admin/banners/create";
    }

    // 3. GUARDAR BANNER
    @PostMapping("/save")
    public String save(@ModelAttribute Banner banner) {
        bannerService.save(banner);
        return "redirect:/admin/banners";
    }

    // 4. MOSTRAR FORMULARIO DE EDICIÓN
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Banner> bannerOpt = bannerService.get(id);
        if (bannerOpt.isPresent()) {
             model.addAttribute("banner", bannerOpt.get());
             model.addAttribute("pageTitle", "Editar Banner");
             model.addAttribute("activeMenu", "banners");
             return "admin/banners/edit";
        }
        return "redirect:/admin/banners";
    }

    // 5. ELIMINAR BANNER
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        bannerService.delete(id);
        return "redirect:/admin/banners";
    }
}