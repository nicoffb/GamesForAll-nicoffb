package com.salesianostriana.dam.flalleryapi.controllers;

import com.salesianostriana.dam.flalleryapi.models.Artwork;
import com.salesianostriana.dam.flalleryapi.models.Comment;
import com.salesianostriana.dam.flalleryapi.models.Loved;
import com.salesianostriana.dam.flalleryapi.models.User;
import com.salesianostriana.dam.flalleryapi.models.dtos.artwork.ArtworkCreateRequest;
import com.salesianostriana.dam.flalleryapi.models.dtos.artwork.ArtworkResponse;
import com.salesianostriana.dam.flalleryapi.models.dtos.PageDto;
import com.salesianostriana.dam.flalleryapi.models.dtos.comment.CommentCreateRequest;
import com.salesianostriana.dam.flalleryapi.models.dtos.comment.CommentResponse;
import com.salesianostriana.dam.flalleryapi.repositories.ArtworkRepository;
import com.salesianostriana.dam.flalleryapi.search.util.SearchCriteria;
import com.salesianostriana.dam.flalleryapi.search.util.SearchCriteriaExtractor;
import com.salesianostriana.dam.flalleryapi.services.ArtworkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class ArtworkController {

    private final ArtworkService artworkService;
    private final ArtworkRepository artworkRepository;


    @Operation(summary = "Get a list of all Artworks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Artworks Found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Artwork.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                     "content": [
                                                         {
                                                             "name": "Guerra Romana en témperas",
                                                             "uuid": "c0a8002c-8659-1f7a-8186-5a0082f10001",
                                                             "comments": [],
                                                             "owner": "Titin",
                                                             "description": "Guerra de los años 90 hecha en témperas"
                                                         }
                                                     ],
                                                     "totalPages": 1,
                                                     "totalElements": 1,
                                                     "pageSize": 1
                                                 }
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No Artworks Found",
                    content = @Content),
    })
    @GetMapping("/artwork")
    public ResponseEntity<PageDto<ArtworkResponse>> search(
            @RequestParam(value = "s", defaultValue = "") String s,
            @PageableDefault(size = 25, page = 0) Pageable pageable) {

        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(s);


        Page<Artwork> result = artworkService.search(params, pageable);
        Page<ArtworkResponse> response = result.map(ArtworkResponse::artworkToArtworkResponse);

        if (result.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new PageDto<ArtworkResponse>(response));
    }


    @Operation(summary = "Get a single Artwork")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Artwork Found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Artwork.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                     "name": "Guerra Romana en témperas",
                                                     "uuid": "c0a8002c-8659-1f7a-8186-5a0082f10001",
                                                     "comments": [],
                                                     "owner": "Titin",
                                                     "description": "Guerra de los años 90 hecha en témperas"
                                                 }
                                            ]                                        \s
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No Artwork Found",
                    content = @Content),
    })
    @GetMapping("/artwork/{id}")
    public ResponseEntity<ArtworkResponse> getArtwork(@PathVariable UUID id){

        Optional<Artwork> artwork = artworkService.findById(id);
        return artwork.map(value -> ResponseEntity.of(Optional.of(ArtworkResponse.artworkToArtworkResponse(value)))).orElseGet(() -> ResponseEntity.notFound().build());

    }


    @Operation(summary = "Create a new Artwork")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Artwork Created Successfully",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Artwork.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                     "name": "Guerra Romana en témperas",
                                                     "uuid": "c0a8002c-8659-1f7a-8186-5a0082f10001",
                                                     "comments": [],
                                                     "owner": "Titin",
                                                     "description": "Guerra de los años 90 hecha en témperas"
                                                 }
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "Bad Artwork Creation Request",
                    content = @Content),
    })
    @PostMapping(value = "/artwork", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ArtworkResponse>createArtwork(
            @RequestPart("artwork") ArtworkCreateRequest artworkCreateRequest,
            @AuthenticationPrincipal User user,
            @RequestPart("file")MultipartFile file){

        Artwork artwork = artworkService.save(artworkCreateRequest,file,user.getUsername());


        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(artwork.getIdArtwork()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(ArtworkResponse.artworkToArtworkResponse(artwork));
    }


    @Operation(summary = "Delete an Artwork")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "No content",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Artwork.class))
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No Artwork found",
                    content = @Content),
    })

    @Transactional
    @DeleteMapping("/artwork/{id}")
    public ResponseEntity<?> delete(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user) {


        Optional<Artwork> artwork = artworkService.findById(id);

        if (artwork.isPresent()){
            artworkService.delete(artwork.get() , user);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();

    }


    @Operation(summary = "Like an Artwork")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Artwork liked Successfully",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Artwork.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "name": "Guerra Romana en témperas",
                                                    "uuid": "c0a8002c-8659-1f7a-8186-5a0082f10001",
                                                    "comments": [],
                                                    "owner": "Titin",
                                                    "description": "Guerra de los años 90 hecha en témperas"
                                                }
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "Bad Artwork like Request",
                    content = @Content),
    })
    @PostMapping("/artwork/{id}/like")
    public ResponseEntity<ArtworkResponse> likeArtwork(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user){

        Artwork artwork = artworkService.likeArtwork(id,user.getUsername());


        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/artwork/{id}/like")
                .buildAndExpand(artwork.getIdArtwork()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(ArtworkResponse.artworkToArtworkResponse(artwork));

    }


    @Operation(summary = "Unlike an Artwork")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "No content",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Loved.class))
                    )})
    })
    @DeleteMapping("/artwork/{id}/like")
    public ResponseEntity<ArtworkResponse> deleteLike(
            @PathVariable UUID idArtwork,
            @AuthenticationPrincipal User user){

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ArtworkResponse
                        .artworkToArtworkResponse(artworkService.unlike(idArtwork,user.getUsername())));

    }


    @Operation(summary = "Comment an Artwork")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Artwork commented Successfully",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Comment.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "text": "Una obra bastante buena",
                                                "writer": "titintoro"
                                            }                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "Bad Artwork comment Request",
                    content = @Content),
    })
    @PostMapping("/artwork/{id}/comment")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable UUID id,
            @RequestBody CommentCreateRequest comment,
            @AuthenticationPrincipal User user){

        Artwork artwork = artworkService.findById(id).get();

        Comment response = comment.commentCreateRequestToComment(artwork,user.getUsername());
        artwork.getComments().add(response);
        artworkRepository.save(artwork);
        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/artwork/{id}/comment")
                .buildAndExpand(response.getIdComment()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(CommentResponse.commentToCommentResponse(response));

    }

    @Operation(summary = "Delete a Comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "No content",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Comment.class))
                    )})
    })
    @DeleteMapping("/artwork/{id}/comment/{idComment}")
    public ResponseEntity<ArtworkResponse> deleteComment(@PathVariable UUID id, UUID idComment, @AuthenticationPrincipal User user){

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ArtworkResponse
                        .artworkToArtworkResponse(artworkService.deleteComment(idComment,id,user.getUsername())));

    }





}

