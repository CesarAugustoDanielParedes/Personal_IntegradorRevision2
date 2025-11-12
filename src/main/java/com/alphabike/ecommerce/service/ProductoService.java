package com.alphabike.ecommerce.service;

import com.alphabike.ecommerce.model.Producto;
import com.alphabike.ecommerce.repository.ProductoRepository;
import com.alphabike.ecommerce.service.upload.IStorageService;
import org.springframework.stereotype.Service; // <-- Soluciona @Service
import org.springframework.web.multipart.MultipartFile; // <-- Soluciona MultipartFile
import java.io.IOException; // <-- Soluciona IOException (para el manejo de archivos)
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final IStorageService storageService;

    // Constructor con inyección de dependencias
    public ProductoService(ProductoRepository productoRepository, IStorageService storageService) {
        this.productoRepository = productoRepository;
        this.storageService = storageService;
    }

    // CREATE / UPDATE
    public Producto save(Producto p, MultipartFile file) throws IOException {
        
        // 1. Manejo de la imagen: si se sube un nuevo archivo
        if (file != null && !file.isEmpty()) {
            // Si el producto ya existe y tiene una imagen anterior, la eliminamos
            if (p.getId() != null) {
                Producto existingProduct = productoRepository.findById(p.getId()).orElse(null);
                // Verifica que la imagen no sea nula o la 'default.png' antes de borrar
                if (existingProduct != null && existingProduct.getImagen() != null && !existingProduct.getImagen().equals("default.png")) {
                    storageService.delete(existingProduct.getImagen());
                }
            }
            
            // 2. Guardar el nuevo archivo y obtener el nombre único
            String nombreImagen = storageService.save(file);
            p.setImagen(nombreImagen);
        } 
        // Si el archivo es nulo y el producto es nuevo, asignamos una imagen por defecto
        else if (p.getImagen() == null || p.getImagen().isEmpty()){
             p.setImagen("default.png");
        }


        // 3. Guardar el producto en la base de datos
        return productoRepository.save(p);
    }
    
    // READ (Todos)
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    // READ (Por ID)
    public Optional<Producto> get(Integer id) {
        return productoRepository.findById(id);
    }

    // DELETE
    public void delete(Integer id) {
        Producto p = get(id).orElse(null);
        if (p != null) {
            if (p.getImagen() != null && !p.getImagen().equals("default.png")) {
                storageService.delete(p.getImagen()); // Elimina el archivo físico
            }
            productoRepository.deleteById(id); // Elimina el registro de la BD
        }
    }
}