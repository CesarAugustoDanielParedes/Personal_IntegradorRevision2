package com.alphabike.ecommerce.controller;

import com.alphabike.ecommerce.model.Producto;
import com.alphabike.ecommerce.service.ProductoService;
import com.alphabike.ecommerce.service.MarcaService;
import com.alphabike.ecommerce.service.CategoriaService;
import com.alphabike.ecommerce.service.BannerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class TiendaController {

    private final ProductoService productoService;
    private final MarcaService marcaService;
    private final CategoriaService categoriaService;
    private final BannerService bannerService;

    // Constructor con Inyección de Dependencias
    public TiendaController(ProductoService productoService, MarcaService marcaService, CategoriaService categoriaService, BannerService bannerService) {
        this.productoService = productoService;
        this.marcaService = marcaService;
        this.categoriaService = categoriaService;
        this.bannerService = bannerService;
    }

    // Ruta: / (Página de Inicio)
    @GetMapping("/")
    public String home(Model model) {
        
        // Carga los primeros 8 productos para la sección de destacados
        model.addAttribute("productosDestacados", productoService.findAll().stream().limit(8).collect(Collectors.toList()));
        
        // Carga los primeros 6 logos para la sección de marcas
        model.addAttribute("marcasPrincipales", marcaService.findAll().stream().limit(6).collect(Collectors.toList()));
        
        // CORRECCIÓN CRÍTICA: Cargar la lista que index.html espera
        model.addAttribute("categoriasPrincipales", categoriaService.findAll().stream().limit(6).collect(Collectors.toList()));
        
        model.addAttribute("banners", bannerService.findAll()); 
        
        return "index"; // Vista principal
    }

    // Ruta: /productos (Listado y Filtros) - Se mantiene sin cambios
    @GetMapping("/productos")
    public String productos(Model model, 
                            @RequestParam(value = "search", required = false) String search,
                            @RequestParam(value = "marcaId", required = false) Integer marcaId,
                            @RequestParam(value = "categoriaId", required = false) Integer categoriaId,
                            @RequestParam(value = "maxPrice", required = false) Double maxPrice) {
        
        Stream<Producto> productosStream = productoService.findAll().stream();
        
        // Lógica de Filtrado (Se mantiene el filtrado en memoria)
        if (search != null && !search.isEmpty()) {
            productosStream = productosStream.filter(p -> p.getNombre().toLowerCase().contains(search.toLowerCase()));
        }
        if (marcaId != null) {
            productosStream = productosStream.filter(p -> p.getMarca() != null && p.getMarca().getId().equals(marcaId));
        }
        if (categoriaId != null) {
            productosStream = productosStream.filter(p -> p.getCategoria() != null && p.getCategoria().getId().equals(categoriaId));
        }
        if (maxPrice != null && maxPrice > 0) {
            productosStream = productosStream.filter(p -> p.getPrecio() <= maxPrice);
        }
        
        List<Producto> productosFiltrados = productosStream.collect(Collectors.toList());

        model.addAttribute("productos", productosFiltrados);
        model.addAttribute("marcas", marcaService.findAll()); 
        model.addAttribute("categorias", categoriaService.findAll()); 
        
        // Enviar valores actuales para mantener el estado del formulario
        model.addAttribute("currentSearch", search);
        model.addAttribute("currentMarca", marcaId);
        model.addAttribute("currentCategoria", categoriaId);
        model.addAttribute("currentMaxPrice", maxPrice);
        
        return "productos"; 
    }
    
    // Ruta: /productos/{id} (Detalle del Producto)
    @GetMapping("/productos/{id}")
    public String detalleProducto(@PathVariable Integer id, Model model, RedirectAttributes attributes) {
        Optional<Producto> productoOpt = productoService.get(id);
        
        if (productoOpt.isPresent()) {
            model.addAttribute("producto", productoOpt.get());
            return "detalle"; 
        }
        
        attributes.addFlashAttribute("error", "Producto no encontrado.");
        return "redirect:/productos";
    }
    
    // Ruta: /contacto
    @GetMapping("/contacto")
    public String contacto() {
        return "contacto"; 
    }
}