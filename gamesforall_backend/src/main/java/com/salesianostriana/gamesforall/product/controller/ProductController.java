package com.salesianostriana.gamesforall.product.controller;

import com.salesianostriana.gamesforall.exception.UserNotFoundException;
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
import com.salesianostriana.gamesforall.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final StorageService storageService;

    private final UserService userService;


    @Operation(summary = "Obtiene todos los productos de forma paginada y con criterios")
    @PageableAsQueryParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado productos",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = EasyProductDTO.class)))
                    }),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado productos",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.gamesforall.exception.EmptyProductListException.class))),
            @ApiResponse(responseCode = "400",
                    description = "La búsqueda es incorrecta",
                    content = @Content)
    })
    @GetMapping("/search")
    public PageDto<EasyProductDTO> getByCriteria(@RequestParam(value = "search", defaultValue = "") String search,
                                          @PageableDefault(size = 8, page = 0) Pageable pageable) {

        List<SearchCriteria> params = Extractor.extractSearchCriteriaList(search);
        PageDto<EasyProductDTO> products = productService.search(params, pageable);

        return products;

    }


    @Operation(summary = "Obtiene el producto a partir de un id dado")
    @PageableAsQueryParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado productos",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BasicProductDTO.class)))
                    }),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el producto",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.gamesforall.exception.ProductNotFoundException.class))),
            @ApiResponse(responseCode = "400",
                    description = "La búsqueda es incorrecta",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public BasicProductDTO getById(@PathVariable Long id) {

        return productService.findById(id);
    }


    @Operation(summary = "Se crea un nuevo producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado el producto",
                    content = {@Content(schema = @Schema(implementation = BasicProductDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Los datos no son válidos",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "Full authentication is required to access this resource",
                    content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<BasicProductDTO> createNewProduct(@RequestPart("body") ProductRequestDTO created, @RequestPart("files") MultipartFile files,@AuthenticationPrincipal User loggedUser) {


        Product product =created.toProduct(created);
        //CARGAR LOS TRADES,hk
        product.setPublication_date(LocalDateTime.now());
        product.setUser(userService.findByUsername(loggedUser.getUsername()).orElseThrow(UserNotFoundException::new));

        productService.add(product,files);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId()).toUri();

        BasicProductDTO converted = BasicProductDTO.of(product);


        return ResponseEntity
                .created(createdURI)
                .body(converted);

    }



    @Operation(summary = "Edita un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha editado el producto",
                    content = {@Content(schema = @Schema(implementation = BasicProductDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Los datos no son válidos",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el producto",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "Full authentication is required to access this resource",
                    content = @Content),
    })
    @PutMapping("/{id}")
    public BasicProductDTO editProduct(@PathVariable Long id, @RequestBody BasicProductDTO edited) {
        return productService.edit(id,edited);
    }





    @Operation(summary = "Borra un producto a partir de un id dado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado el producto con éxito",
                    content = {}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el producto",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.gamesforall.exception.ProductNotFoundException.class))),
            @ApiResponse(responseCode = "401",
                    description = "Full authentication is required to access this resource",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
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





}

