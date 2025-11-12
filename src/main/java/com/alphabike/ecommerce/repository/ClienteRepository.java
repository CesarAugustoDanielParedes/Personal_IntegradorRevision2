package com.alphabike.ecommerce.repository;

import com.alphabike.ecommerce.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    // Spring Data JPA lo implementa automáticamente al ver el prefijo 'findBy'
    Cliente findByEmail(String email);
}