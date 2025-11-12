package com.alphabike.ecommerce.service;

import com.alphabike.ecommerce.model.Categoria;
import com.alphabike.ecommerce.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // CREATE / UPDATE
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
    
    // READ (Todos)
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    // READ (Por ID)
    public Optional<Categoria> get(Integer id) {
        return categoriaRepository.findById(id);
    }

    // DELETE
    public void delete(Integer id) {
        categoriaRepository.deleteById(id);
    }
}