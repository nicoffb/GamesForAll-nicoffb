package com.salesianostriana.gamesforall.product.controller;

import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.product.repository.ProductRepository;
import com.salesianostriana.gamesforall.product.service.ProductService;
import com.salesianostriana.gamesforall.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/note")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository repository;

    @GetMapping("/")
    public List<Product> getAll(@AuthenticationPrincipal User user) {
        //no debemos devolver ya responentity no? si no la clase directamente o el dto mejor?
        return productService.findAll();
        //return buildResponseOfAList(repository.findByAuthor(user.getId().toString()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        /*
            El método ResponseEntity.of recibe como argumento un Optional<?> y devuelve
                - 200 Ok si Optional.isPresent() == true
                - 404 Not Found si Optional.isEmpty() == true
         */
        return ResponseEntity.of(repository.findById(id));
    }


//    @GetMapping("/author/{author}")
//    public ResponseEntity<List<Product>> getByAuthor(@PathVariable String author) {
//        // Utilizamos un método comun para devolver la respuesta de todos los List<Note>
//        return buildResponseOfAList(repository.findByAuthor(author));
//    }

    /**
     * Este método sirve para devolver la respuesta de un List<Note>
     * @param list Lista que vendrá de una consulta en el repositorio
     * @return 404 si la lista está vacía, 200 OK si la lista tiene elementos
     */
    private ResponseEntity<List<Product>> buildResponseOfAList(List<Product> list) {

        if (list.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(list);


    }

    @PostMapping("/")
    public ResponseEntity<Product> createNewNote(@RequestBody Product product) {

        Product created = repository.save(product);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        /*
            Habitualmente, la respuesta correcta de una petición POST es 201 Created.
            Adicionalmente, se puede devolver un encabezado Location con la URI que
            nos permite realizar la petición GET al recurso recién creado.
         */
        return ResponseEntity
                .created(createdURI)
                .body(created);

    }

    @PreAuthorize("@noteRepository.findById(#id).orElse(new net.openwebinars.springboot.restjwt.note.model.Note()).author == authentication.principal.getId().toString()")
    @PutMapping("/{id}")
    public ResponseEntity<Product> edit(@PathVariable Long id, @RequestBody Product edited) {

        return ResponseEntity.of(
                repository.findById(id)
                        .map(product -> {
                            product.setTitle(edited.getTitle());
                            product.setDescription(edited.getDescription());
                            //note.setAuthor(edited.getAuthor());
                            //product.setImportant(edited.isImportant());
                            return repository.save(product);
                        }));



    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        // Dejamos esta línea comentada para provocar un error 500 si eliminamos dos veces un mismo recurso
        //if (repository.existsById(id))
        repository.deleteById(id);

        return ResponseEntity.noContent().build();

    }




}

