package com.salesianostriana.meal.service.storage;

import com.salesianostriana.meal.error.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemService implements StorageService{

    @Value("${storage.location}")
    private String storageLocation;
    private Path rootLocation;

    @Override
    @PostConstruct
    public void init() {
        rootLocation = Paths.get(storageLocation);
        try{
            Files.createDirectories(rootLocation);
        }catch (IOException e) {
            throw new StorageException("No se puede inilializar el almacenamiento de archivos", e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        try {
            return store(file.getBytes(), file.getOriginalFilename(), file.getContentType());
        } catch (Exception ex) {
            throw new StorageException("Error guardando archivo: " + file.getOriginalFilename(), ex);
        }
    }

    @Override
    public String store(byte[] file, String filename, String contentType) {
        String newFilename = StringUtils.cleanPath(filename);
        if (file.length == 0)
            throw new StorageException("El archivo está vacío");
        newFilename = calculateNewFilename(newFilename);
        try (InputStream inputStream = new ByteArrayInputStream(file)) {
            Files.copy(inputStream, rootLocation.resolve(newFilename));
        } catch(IOException ex) {
            throw new StorageException("Error al guardar el archivo: " + newFilename, ex);
        }
        return newFilename;
    }

    private String calculateNewFilename(String filename) {
        String newFilename = filename;
        while(Files.exists(rootLocation.resolve(newFilename))) {
            String extension = StringUtils.getFilenameExtension(newFilename);
            String name = newFilename.replace("." + extension, "");
            newFilename = name + "_" + System.currentTimeMillis() + "." + extension;
        }
        return newFilename;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(rootLocation)
                    .filter(path -> !path.equals(rootLocation))
                    .map(rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Error leyendo los archivos", e);
        }
    }

    @Override
    public Path load(String filename) {return rootLocation.resolve(filename);}

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            MediaUrlResource resource = new MediaUrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException("No se puede leer el archivo: " + filename);
            }
        } catch (MalformedURLException ex) {
            throw new StorageException("No se puede leer el archivo: " + filename);
        }
    }

    @Override
    public void deleteFile(String filename) {
        try {
            Files.delete(load(filename));
        } catch (IOException e) {
            throw new StorageException("No se pudo borrar el archivo:" + filename);
        }
    }

    @Override
    public void deleteAll() {
        try {
            FileSystemUtils.deleteRecursively(rootLocation);
        } catch (IOException e) {
            throw new StorageException("No se pudo borrar los archivos");
        }
    }

}
