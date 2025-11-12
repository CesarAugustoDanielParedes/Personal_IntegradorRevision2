package com.alphabike.ecommerce.service;

import com.alphabike.ecommerce.model.Banner;
import com.alphabike.ecommerce.repository.BannerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BannerService {

    private final BannerRepository bannerRepository;
    // Nota: Aquí se inyectaría IStorageService si la imagen se subiera, 
    // pero por ahora usaremos una URL simple o un nombre de archivo.

    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    // CREATE / UPDATE
    public Banner save(Banner banner) {
        return bannerRepository.save(banner);
    }
    
    // READ (Todos, ordenados por orden ascendente para el carousel)
    public List<Banner> findAll() {
        return bannerRepository.findAllByOrderByOrdenAsc();
    }

    // READ (Por ID)
    public Optional<Banner> get(Integer id) {
        return bannerRepository.findById(id);
    }

    // DELETE
    public void delete(Integer id) {
        bannerRepository.deleteById(id);
    }
}