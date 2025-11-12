package com.alphabike.ecommerce.repository;

import com.alphabike.ecommerce.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {
    
    // Spring Data JPA crea esta consulta automáticamente:
    // "Find All (Banner) By Order By Orden (campo de la entidad) Ascending"
    List<Banner> findAllByOrderByOrdenAsc();
}