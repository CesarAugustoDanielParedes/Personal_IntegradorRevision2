package com.alphabike.ecommerce.service;

import com.alphabike.ecommerce.model.Cliente;
import com.alphabike.ecommerce.repository.ClienteRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Importar BCrypt
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final BCryptPasswordEncoder passwordEncoder; // Nuevo

    public ClienteService(ClienteRepository clienteRepository, BCryptPasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder; // Inyectar BCrypt
    }

    // CREATE / UPDATE
    public Cliente save(Cliente cliente) {
        // Cifrar la contraseña ANTES de guardar si es un nuevo registro
        if (cliente.getId() == null || cliente.getId() == 0) {
             // Solo ciframos si la contraseña no es un hash (nuevo registro)
             if (!cliente.getPassword().startsWith("$2a$")) {
                 cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
             }
        }
        return clienteRepository.save(cliente);
    }
    
    // READ (Todos, Get, Delete y findByEmail se mantienen igual)
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> get(Integer id) {
        return clienteRepository.findById(id);
    }
    
    public Cliente findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public void delete(Integer id) {
        clienteRepository.deleteById(id);
    }
}