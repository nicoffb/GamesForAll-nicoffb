package com.trianaSalesianos.tofuApp.controller;

import com.trianaSalesianos.tofuApp.model.Category;
import com.trianaSalesianos.tofuApp.model.DietDays;
import com.trianaSalesianos.tofuApp.model.User;
import com.trianaSalesianos.tofuApp.model.dto.category.CategoryRequest;
import com.trianaSalesianos.tofuApp.model.dto.category.CategoryResponse;
import com.trianaSalesianos.tofuApp.model.dto.dietDay.DietDaysRequest;
import com.trianaSalesianos.tofuApp.model.dto.dietDay.DietDaysResponse;
import com.trianaSalesianos.tofuApp.model.dto.page.PageDto;
import com.trianaSalesianos.tofuApp.service.CategoryService;
import com.trianaSalesianos.tofuApp.service.DietDaysService;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Tag(name = "DietDays", description = "Diet days controllers")
@RestController
@RequiredArgsConstructor
@RequestMapping("/dietday")
//@CrossOrigin(origins = "http://localhost:4200")

public class DietDaysController {
    private final DietDaysService dietDaysService;
    @GetMapping("")
    public PageDto<DietDaysResponse> getAll(
            @RequestParam(value = "search", defaultValue = "") String search,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        return dietDaysService.getAllBySearch(search, pageable);
    }
    @GetMapping("/{id}")
    public DietDaysResponse getById(
            @Parameter(description = "Id of the diet day to get")
            @PathVariable UUID id) {
        return DietDaysResponse.fromDietDay(dietDaysService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<DietDaysResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data required to create a diet day")
            @Valid @RequestBody DietDaysRequest dietDaysRequest,
            @AuthenticationPrincipal User user
    ) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dayStr = dietDaysRequest.getDay();
        LocalDate dateTime = LocalDate.parse(dayStr, formatter);

        DietDays created = DietDays.builder()
                .day(dateTime)
                .user(user)
                .build();

        dietDaysService.save(created);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(DietDaysResponse.fromDietDay(created));
    }
    @PutMapping("/{id}")
    public DietDaysResponse update(
            @Parameter(description = "Id of the diet day to edit")
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New name for the diet day")
            @Valid @RequestBody DietDaysRequest dietDaysRequest) {
        return dietDaysService.update(dietDaysRequest, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeDietDay(
            @PathVariable UUID id
    ) {

        dietDaysService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id_diet}/recipe/{id_recipe}")
    public DietDaysResponse addRecipeToDiet(
            @PathVariable UUID id_diet,
            @PathVariable UUID id_recipe,
            @AuthenticationPrincipal User user
    ) {
        return dietDaysService.addRecipeToDiet(id_diet, id_recipe, user);
    }

    @DeleteMapping("/{id_diet}/recipe/{id_recipe}")
    public ResponseEntity<?> removeRecipeFromDiet(
            @PathVariable UUID id_diet,
            @PathVariable UUID id_recipe,
            @AuthenticationPrincipal User user
    ){
        dietDaysService.removeRecipeFromDiet(id_diet,id_recipe,user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
