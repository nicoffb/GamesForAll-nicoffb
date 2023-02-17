package com.salesianostriana.gamesforall.product.controller;

import com.salesianostriana.gamesforall.product.dto.BasicProductDTO;
import com.salesianostriana.gamesforall.product.dto.EasyProductDTO;
import com.salesianostriana.gamesforall.product.dto.PageDto;
import com.salesianostriana.gamesforall.product.dto.ProductRequestDTO;
import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.product.service.ProductService;
import com.salesianostriana.gamesforall.search.util.Extractor;
import com.salesianostriana.gamesforall.search.util.SearchCriteria;
import com.salesianostriana.gamesforall.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/")
    public PageDto<EasyProductDTO> getAll(@AuthenticationPrincipal User user ,
                                       @PageableDefault(size = 3, page = 0) Pageable pageable) {

        return new PageDto<EasyProductDTO>(productService.findAll(pageable));
        //devolver directamente como pagedto en el search , que haga la conversion en el servicio sin el new aquí
    }

    @GetMapping("/search")
    public PageDto<EasyProductDTO> getByCriteria(@AuthenticationPrincipal User user ,@RequestParam(value = "search", defaultValue = "") String search,
                                          @PageableDefault(size = 3, page = 0) Pageable pageable) {

        List<SearchCriteria> params = Extractor.extractSearchCriteriaList(search);
        PageDto<EasyProductDTO> products = productService.search(params, pageable);
        // limpiar el más adelante
        return products;

    }





    @GetMapping("/{id}")
    public EasyProductDTO getById(@PathVariable Long id) {

        return productService.findById(id);
    }



    @PostMapping("/")
    public ResponseEntity<BasicProductDTO> createNewProduct(@RequestBody ProductRequestDTO created) {


        Product product =created.toProduct(created); //estabien invocarlo con created?
        productService.add(product);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId()).toUri();

        BasicProductDTO converted = BasicProductDTO.of(product);

        return ResponseEntity
                .created(createdURI)
                .body(converted);

        //gestionar el fallo con bad request o manejo de errores
    }

   // @PreAuthorize("@noteRepository.findById(#id).orElse(new net.openwebinars.springboot.restjwt.note.model.Note()).author == authentication.principal.getId().toString()")
    @PutMapping("/{id}")
    public BasicProductDTO editProduct(@PathVariable Long id, @RequestBody BasicProductDTO edited) {
        //editar en el servicio , no devuelve ResponseEntity, sino
        return productService.edit(id,edited);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        // Dejamos esta línea comentada para provocar un error 500 si eliminamos dos veces un mismo recurso
        //if (repository.existsById(id))
        productService.deleteById(id);

        return ResponseEntity.noContent().build();

    }




//    @GetMapping("/author/{author}")
//    public ResponseEntity<List<Product>> getByAuthor(@PathVariable String author) {
//        // Utilizamos un método comun para devolver la respuesta de todos los List<Note>
//        return buildResponseOfAList(repository.findByAuthor(author));
//    }
//
//    /**
//     * Este método sirve para devolver la respuesta de un List<Note>
//     * @param list Lista que vendrá de una consulta en el repositorio
//     * @return 404 si la lista está vacía, 200 OK si la lista tiene elementos
//     */
//    private ResponseEntity<List<Product>> buildResponseOfAList(List<Product> list) {
//
//        if (list.isEmpty())
//            return ResponseEntity.notFound().build();
//        else
//            return ResponseEntity.ok(list);
//
//
//    }



}

