// src/main/java/com/alphabike/ecommerce/service/upload/IStorageService.java
package com.alphabike.ecommerce.service.upload;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface IStorageService {
    /**
     * Guarda el archivo subido y devuelve su nombre único.
     */
    String save(MultipartFile file) throws IOException;

    /**
     * Elimina un archivo del sistema de almacenamiento.
     */
    void delete(String filename);
}