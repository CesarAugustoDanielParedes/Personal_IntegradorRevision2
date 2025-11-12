// src/main/java/com/alphabike/ecommerce/service/upload/FileSystemStorageService.java
package com.alphabike.ecommerce.service.upload;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileSystemStorageService implements IStorageService {

    // Ruta donde se guardarán los archivos. Usamos 'productos' como subdirectorio.
    private final Path rootLocation = Paths.get("src/main/resources/static/images/productos");

    // Constructor para asegurar que el directorio exista
    public FileSystemStorageService() throws IOException {
        // Crea el directorio si no existe (la primera vez que se ejecuta)
        if (!Files.exists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }
    }

    @Override
    public String save(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null; // No hay archivo para guardar
        }

        // 1. Generar nombre de archivo único para evitar colisiones
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + extension;

        // 2. Guardar el archivo en el sistema de archivos
        Path destinationFile = this.rootLocation.resolve(Paths.get(uniqueFilename));
        
        Files.copy(file.getInputStream(), destinationFile);

        // 3. Devolver el nombre único para guardarlo en la base de datos
        return uniqueFilename;
    }

    @Override
    public void delete(String filename) {
        if (filename != null && !filename.isEmpty()) {
            Path fileToDelete = rootLocation.resolve(filename);
            try {
                Files.deleteIfExists(fileToDelete);
            } catch (IOException e) {
                // Manejo de errores de eliminación
                System.err.println("Error al eliminar el archivo: " + filename + ". Causa: " + e.getMessage());
            }
        }
    }
}