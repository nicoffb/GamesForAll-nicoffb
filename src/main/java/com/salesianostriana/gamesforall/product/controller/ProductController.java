package com.salesianostriana.gamesforall.product.controller;

import com.salesianostriana.gamesforall.files.service.StorageService;
import com.salesianostriana.gamesforall.files.utils.MediaTypeUrlResource;
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
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final StorageService storageService;


    @GetMapping("/")
    public PageDto<EasyProductDTO> getAll(@AuthenticationPrincipal User user ,
                                       @PageableDefault(size = 3, page = 0) Pageable pageable) {

        return new PageDto<EasyProductDTO>(productService.findAll(pageable));
        //devolver directamente como pagedto en el search , que haga la conversion en el servicio sin el new aquí
    }

    //buscar tooooodos
    @GetMapping("/search")
    public PageDto<EasyProductDTO> getByCriteria(@AuthenticationPrincipal User user ,@RequestParam(value = "search", defaultValue = "") String search,
                                          @PageableDefault(size = 3, page = 0) Pageable pageable) {

        List<SearchCriteria> params = Extractor.extractSearchCriteriaList(search);
        PageDto<EasyProductDTO> products = productService.search(params, pageable);
        // limpiar el más adelante
        return products;

    }

    @GetMapping("/search/{id}")
    public PageDto<EasyProductDTO> getByCriteria2(@AuthenticationPrincipal User user , @RequestParam(value = "search", defaultValue = "") String search,
                                                  @PageableDefault(size = 3, page = 0) Pageable pageable, @PathVariable UUID id) {

        List<SearchCriteria> params = Extractor.extractSearchCriteriaList(search);

        //user:userId
        params.add(new SearchCriteria("user",":",id));

        PageDto<EasyProductDTO> products = productService.search(params, pageable);
        // limpiar el más adelante
        return products;

    }


    @GetMapping("/{id}")
    public EasyProductDTO getById(@PathVariable Long id) {

        return productService.findById(id);
    }



    @PostMapping("/")
    public ResponseEntity<BasicProductDTO> createNewProduct(@RequestPart("body") ProductRequestDTO created, @RequestPart("files") MultipartFile files) {


        Product product =created.toProduct(created); //estabien invocarlo con created?

        productService.add(product,files);

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


    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename){
        MediaTypeUrlResource resource =
                (MediaTypeUrlResource) storageService.loadAsResource(filename);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", resource.getType())
                .body(resource);
}

//un controller para eliminar foto? y editar foto solo? o meterlo en el de editar producto



}

