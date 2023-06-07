package com.salesianostriana.meal.service.storage;

import com.salesianostriana.meal.error.exception.StorageException;
import org.apache.tika.Tika;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class MediaUrlResource extends UrlResource {

    public MediaUrlResource(URL url) {
        super(url);
    }

    public MediaUrlResource(URI uri) throws MalformedURLException {
        super(uri);
    }

    public MediaUrlResource(String path) throws MalformedURLException {
        super(path);
    }

    public MediaUrlResource(String protocol, String location) throws MalformedURLException {
        super(protocol, location);
    }

    public MediaUrlResource(String protocol, String location, String fragment) throws MalformedURLException {
        super(protocol, location, fragment);
    }

    public String getType() {
        Tika t = new Tika();
        try {
            return t.detect(this.getFile());
        } catch (IOException ex) {
            throw new StorageException("Erorr al obtener el tipo MIME", ex);
        }
    }
}
