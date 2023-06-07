package com.trianaSalesianos.tofuApp.controller;

import com.trianaSalesianos.tofuApp.model.Category;
import com.trianaSalesianos.tofuApp.model.Recipe;
import com.trianaSalesianos.tofuApp.model.Step;
import com.trianaSalesianos.tofuApp.model.User;
import com.trianaSalesianos.tofuApp.model.dto.category.CategoryRequest;
import com.trianaSalesianos.tofuApp.model.dto.category.CategoryResponse;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientResponse;
import com.trianaSalesianos.tofuApp.model.dto.page.PageDto;
import com.trianaSalesianos.tofuApp.model.dto.step.StepRequest;
import com.trianaSalesianos.tofuApp.model.dto.step.StepResponse;
import com.trianaSalesianos.tofuApp.service.CategoryService;
import com.trianaSalesianos.tofuApp.service.StepService;
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
import java.util.List;
import java.util.UUID;

@Tag(name= "Step", description = "Steps controllers")
@RestController
@RequiredArgsConstructor
@RequestMapping("/step")
//@CrossOrigin(origins = "http://localhost:4200")

public class StepController {
    private final StepService stepService;

    @GetMapping("")
    public PageDto<StepResponse> getAll(
            @RequestParam(value = "search", defaultValue = "") String search,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ){
        return stepService.getAllBySearch(search, pageable);
    }
    @GetMapping("/recipe/{idRecipe}")
    public List<StepResponse> getStepsFromRecipe(
            @PathVariable UUID idRecipe
    ){
        return stepService.getStepsByRecipe(idRecipe);
    }

    @GetMapping("/{id}")
    public StepResponse getById(
            @Parameter(description = "Id of the step to get")
            @PathVariable UUID id) {
        return StepResponse.fromStep(stepService.findById(id));
    }
    @PostMapping("/recipe/{id}")
    public ResponseEntity<StepResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data required to create a step")
            @Valid @RequestBody StepRequest stepRequest,
            @AuthenticationPrincipal User user,
            @PathVariable UUID id
    ) {
        Step created = stepService.createStep(user,stepRequest,id);

        stepService.save(created);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(StepResponse.fromStep(created));
    }



    @PutMapping("/{id}")
    public StepResponse update(
            @Parameter(description = "Id of the category to edit")
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New name for the category")
            @Valid @RequestBody StepRequest stepRequest){
        return stepService.update(stepRequest,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeCategory(
            @PathVariable UUID id
    ){

        stepService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
