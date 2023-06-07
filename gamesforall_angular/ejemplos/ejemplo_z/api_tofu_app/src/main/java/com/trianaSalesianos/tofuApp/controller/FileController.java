package com.trianaSalesianos.tofuApp.controller;

import com.trianaSalesianos.tofuApp.model.dto.file.FileResponse;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientResponse;
import com.trianaSalesianos.tofuApp.service.files.StorageService;
import com.trianaSalesianos.tofuApp.utils.MediaTypeUrlResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Tag(name = "Files", description = "Files controller")
@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")

public class FileController {

    private final StorageService storageService;

    @Operation(summary = "Uploading multiple files")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "File uploaded succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FileResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "name": "koalaSad.jpg",
                                                    "uri": "http://localhost:8080/download/koalaSad.jpg",
                                                    "type": "image/jpeg",
                                                    "size": 51423
                                                }                                                                    
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "There was an error with the data provided",
                    content = @Content),
    })
    @PostMapping("/upload/files")
    public ResponseEntity<?> upload(
            @RequestBody(description = "Files to upload")
            @RequestPart("files") MultipartFile[] files) {

        //FileResponse response = uploadFile(file);

        List<FileResponse> result = Arrays.stream(files)
                .map(this::uploadFile)
                .toList();

        return ResponseEntity
                //.created(URI.create(response.getUri()))
                .status(HttpStatus.CREATED)
                .body(result);
    }
    @Operation(summary = "Uploading a file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "File uploaded succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FileResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "name": "koalaSad.jpg",
                                                    "uri": "http://localhost:8080/download/koalaSad.jpg",
                                                    "type": "image/jpeg",
                                                    "size": 51423
                                                }                                                                    
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "There was an error with the data provided",
                    content = @Content),
    })
    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @RequestBody(description = "File to upload")
            @RequestPart("file") MultipartFile file) {

        FileResponse response = uploadFile(file);

        return ResponseEntity.created(URI.create(response.getUri())).body(response);
    }

    private FileResponse uploadFile(MultipartFile file) {

        String name = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(name)
                .toUriString();

        return  FileResponse.builder()
                .name(name)
                .size(file.getSize())
                .type(file.getContentType())
                .uri(uri)
                .build();

    }

    @Operation(summary = "Download an image from the server")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ingredient fetched succesfully",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Resource.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                Resource
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "Image not found",
                    content = @Content),
    })
    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> getFile(
            @Parameter(description = "Name of the image and its extension")
            @PathVariable String filename){
        MediaTypeUrlResource resource =
                (MediaTypeUrlResource) storageService.loadAsResource(filename);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", resource.getType())
                .body(resource);
    }

}
