package com.trianaSalesianos.tofuApp.controller;

import com.trianaSalesianos.tofuApp.model.Category;
import com.trianaSalesianos.tofuApp.model.Type;
import com.trianaSalesianos.tofuApp.model.User;
import com.trianaSalesianos.tofuApp.model.dto.category.CategoryRequest;
import com.trianaSalesianos.tofuApp.model.dto.category.CategoryResponse;
import com.trianaSalesianos.tofuApp.model.dto.page.PageDto;
import com.trianaSalesianos.tofuApp.model.dto.type.TypeRequest;
import com.trianaSalesianos.tofuApp.model.dto.type.TypeResponse;
import com.trianaSalesianos.tofuApp.service.CategoryService;
import com.trianaSalesianos.tofuApp.service.TypeService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@Tag(name= "Type", description = "Types controllers")
@RestController
@RequiredArgsConstructor
@RequestMapping("/type")
//@CrossOrigin(origins = "http://localhost:4200")

public class TypeController {
    private final TypeService typeService;
    @GetMapping("")
    public PageDto<TypeResponse> getAll(
            @RequestParam(value = "search", defaultValue = "") String search,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ){
        return typeService.getAllBySearch(search, pageable);
    }
    @GetMapping("/{id}")
    public TypeResponse getById(
            @Parameter(description = "Id of the type to get")
            @PathVariable UUID id) {
        return TypeResponse.fromType(typeService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<TypeResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data required to create a type")
            @Valid @RequestBody TypeRequest typeRequest
    ) {
        Type created = Type.builder()
                .name(typeRequest.getName())
                .build();

        typeService.save(created);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(TypeResponse.fromType(created));
    }
    @PutMapping("/{id}")
    public TypeResponse update(
            @Parameter(description = "Id of the type to edit")
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New name for the type")
            @Valid @RequestBody TypeRequest typeRequest){
        return typeService.update(typeRequest,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeType(
            @PathVariable UUID id
    ){

        typeService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
