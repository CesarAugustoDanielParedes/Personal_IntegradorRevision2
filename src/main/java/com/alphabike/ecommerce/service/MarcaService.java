package com.alphabike.ecommerce.service;

import com.alphabike.ecommerce.model.Marca;
import com.alphabike.ecommerce.repository.MarcaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MarcaService {

    private final MarcaRepository marcaRepository;

    public MarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    // CREATE / UPDATE
    public Marca save(Marca marca) {
        return marcaRepository.save(marca);
    }
    
    // READ (Todos)
    public List<Marca> findAll() {
        return marcaRepository.findAll();
    }

    // READ (Por ID)
    public Optional<Marca> get(Integer id) {
        return marcaRepository.findById(id);
    }

    // DELETE
    public void delete(Integer id) {
        marcaRepository.deleteById(id);
    }
}