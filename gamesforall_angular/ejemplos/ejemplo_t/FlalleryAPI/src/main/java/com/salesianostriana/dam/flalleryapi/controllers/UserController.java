package com.salesianostriana.dam.flalleryapi.controllers;

import com.salesianostriana.dam.flalleryapi.models.Artwork;
import com.salesianostriana.dam.flalleryapi.models.Comment;
import com.salesianostriana.dam.flalleryapi.models.UserRole;
import com.salesianostriana.dam.flalleryapi.models.dtos.artwork.ArtworkResponse;
import com.salesianostriana.dam.flalleryapi.models.dtos.comment.CommentResponse;
import com.salesianostriana.dam.flalleryapi.models.dtos.user.*;
import com.salesianostriana.dam.flalleryapi.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import com.salesianostriana.dam.flalleryapi.security.jwt.access.JwtProvider;
import com.salesianostriana.dam.flalleryapi.security.jwt.refresh.RefreshToken;
import com.salesianostriana.dam.flalleryapi.security.jwt.refresh.RefreshTokenException;
import com.salesianostriana.dam.flalleryapi.security.jwt.refresh.RefreshTokenRequest;
import com.salesianostriana.dam.flalleryapi.security.jwt.refresh.RefreshTokenService;
import com.salesianostriana.dam.flalleryapi.models.User;
import com.salesianostriana.dam.flalleryapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;


    @Operation(summary = "Register a new User with user role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "User Created",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = User.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "ac1b033c-8678-142c-8186-785461290000",
                                                "username": "titintoro",
                                                "avatar": "https://avatar.com",
                                                "fullName": "Valentín Tola",
                                                "createdAt": "22/02/2023 09:54:01"
                                            }                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content),
    })
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> createUserWithUserRole(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUserWithUserRole(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }

    @PutMapping("/auth/editUser")
    public ResponseEntity<UserResponse> editMyUser(@Valid @RequestBody UserEditRequest userEditRequest) {
        Optional<User> userResponse= userService.edit(userEditRequest);

        return userResponse.map(user -> ResponseEntity.status(HttpStatus.OK).body(UserResponse.fromUser(user))).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(summary = "Get a list of all Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Users Found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = User.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                 {
                                                     "id": "751c3611-8882-5751-9f0d-d216dd047ebc",
                                                     "username": "mcampos",
                                                     "avatar": "https://icon-library.com/images/avatar-icon-images/avatar-icon-images-4.jpg",
                                                     "fullName": "Miguel",
                                                     "createdAt": "26/09/2021 00:00:00"
                                                 },
                                                 {
                                                     "id": "e641d9ec-ca50-4002-b87b-464b6c7686a9",
                                                     "username": "lmlopez",
                                                     "avatar": "https://icon-library.com/images/avatar-icon-images/avatar-icon-images-4.jpg",
                                                     "fullName": "Luis Miguel",
                                                     "createdAt": "26/09/2021 00:00:00"
                                                 },
                                                 {
                                                     "id": "ac1b033c-8683-171d-8186-83372eeb0000",
                                                     "username": "titintoro",
                                                     "avatar": "https://avatar.com",
                                                     "fullName": "Valentín Tola",
                                                     "createdAt": "24/02/2023 12:37:57"
                                                 }
                                             ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No Users Found",
                    content = @Content),
    })
    @GetMapping("/auth/user/")
    public ResponseEntity<List<UserResponse>>getAllUSers(@AuthenticationPrincipal User user){
        if (user.getRoles().contains(UserRole.ADMIN)){

            List<UserResponse> responseList = userService.findAll().stream().map(UserResponse::fromUser).toList();
            if (responseList.isEmpty())
                return ResponseEntity.notFound().build();

            return ResponseEntity.ok(responseList);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @Operation(summary = "Register a new User with admin role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "User Created",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = User.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "ac1b033c-8678-142c-8186-785461290000",
                                                "username": "titintoro",
                                                "avatar": "https://avatar.com",
                                                "fullName": "Valentín Tola",
                                                "createdAt": "22/02/2023 09:54:01"
                                            }                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content),
    })
    @PostMapping("/auth/register/admin")
    public ResponseEntity<UserResponse> createUserWithAdminRole(@RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUserWithAdminRole(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }


    @PutMapping("/user/{id}/role/")
    public ResponseEntity<UserResponse> swapUserRole(@PathVariable UUID id, @AuthenticationPrincipal User user){
        if (user.getRoles().contains(UserRole.ADMIN)){
            return ResponseEntity.ok(UserResponse.fromUser(userService.swapUserRole(id).get()));
        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @Operation(summary = "Log in with a registered user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "User logged",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = User.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "ac1b033c-8678-142c-8186-785461290000",
                                                "username": "titintoro",
                                                "avatar": "https://avatar.com",
                                                "fullName": "Valentín Tola",
                                                "createdAt": "22/02/2023 09:54:01",
                                                "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhYzFiMDMzYy04Njc4LTE0MmMtODE4Ni03ODU0NjEyOTAwMDAiLCJpYXQiOjE2NzcwNTYzNTYsImV4cCI6MTY3NzA1ODE1Nn0.yZ3LiSL3CY2v312upYuYutZYhSRVEq_fcARTRRhW0ZUDE7luxyUEskFhlHav_31H_o6ZNP1q8WHGrbCB9BMlrg",
                                                "refreshToken": "44b1fea1-aef4-4f86-82b7-e9823ad4d5a9"
                                            }                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content),
    })

    @PostMapping("/auth/login")
    public ResponseEntity<JwtUserResponse> login(@RequestBody LoginRequest loginRequest) {

        // Realizamos la autenticación

        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()
                        )
                );

        // Una vez realizada, la guardamos en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Devolvemos una respuesta adecuada
        String token = jwtProvider.generateToken(authentication);

        User user = (User) authentication.getPrincipal();

        // Eliminamos el token (si existe) antes de crearlo, ya que cada usuario debería tener solamente un token de refresco simultáneo
        refreshTokenService.deleteByUser(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JwtUserResponse.of(user, token, refreshToken.getToken()));


    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verify)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtProvider.generateToken(user);
                    refreshTokenService.deleteByUser(user);
                    RefreshToken refreshToken2 = refreshTokenService.createRefreshToken(user);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(JwtUserResponse.builder()
                                    .token(token)
                                    .refreshToken(refreshToken2.getToken())
                                    .build());
                })
                .orElseThrow(() -> new RefreshTokenException("Refresh token not found"));

    }

    @Operation(summary = "Get a list of all liked Artworks by a User")
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
    @GetMapping("/user/artwork/like")
    public ResponseEntity<List<ArtworkResponse>> getAllLikedArtworksOfAUser(
            @AuthenticationPrincipal User user){


        List<Artwork> result = userService.findArtworksLikedByUser(user.getUsername());

        List<ArtworkResponse> response = result.stream().map(ArtworkResponse::artworkToArtworkResponse).toList();


        if (response.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(response);

    }


    @Operation(summary = "Get a list of all Artworks owned by a User")
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
    @GetMapping("/user/artwork")
    public ResponseEntity<List<ArtworkResponse>> getAllArtworksOfAUser(
            @AuthenticationPrincipal User user){


        List<Artwork> result = userService.findArtworksOfAUser(user.getUsername());

        List<ArtworkResponse> response = result.stream().map(ArtworkResponse::artworkToArtworkResponse).toList();


        if (response.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(response);

    }

    @PutMapping("/user/{id}/changeStatus")
    public ResponseEntity<UserResponse> changeEnabledStatus(@PathVariable UUID id, @AuthenticationPrincipal User userLogged){

        if (userLogged.getRoles().contains(UserRole.ADMIN)){

            Optional<User> user = userService.changeEnabledStatus(id);

            if (user.isEmpty()){
                return ResponseEntity.notFound().build();
            }

            UserResponse userResponse = UserResponse.fromUser(user.get());

            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @Operation(summary = "Get a list of all Comments wrote by a User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Comments Found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Comment.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                 {
                                                     "text": "Interesante !!",
                                                     "writer": "titintoro"
                                                 },
                                                 {
                                                     "text": "Muy chulo",
                                                     "writer": "titintoro"
                                                 },
                                                 {
                                                     "text": "No me gusta",
                                                     "writer": "titintoro"
                                                 }
                                             ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No Comments Found",
                    content = @Content),
    })
    @GetMapping("/user/comment")
    public ResponseEntity<List<CommentResponse>> getAllCommentsOfAUser(
            @AuthenticationPrincipal User user) {

        List<Comment> result = userService.findAllCommentsOfAUser(user.getUsername());

        List<CommentResponse> response = result.stream().map(CommentResponse::commentToCommentResponse).toList();


        if (response.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(response);
    }



    @PutMapping("/user/changePassword")
    public ResponseEntity<UserResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,
                                                       @AuthenticationPrincipal User loggedUser) {

        // Este código es mejorable.
        // La validación de la contraseña nueva se puede hacer con un validador.
        // La gestión de errores se puede hacer con excepciones propias
        try {
            if (userService.passwordMatch(loggedUser, changePasswordRequest.getOldPassword())) {
                Optional<User> modified = userService.editPassword(loggedUser.getId(), changePasswordRequest.getNewPassword());
                if (modified.isPresent())
                    return ResponseEntity.ok(UserResponse.fromUser(modified.get()));
            } else {
                // Lo ideal es que esto se gestionara de forma centralizada
                // Se puede ver cómo hacerlo en la formación sobre Validación con Spring Boot
                // y la formación sobre Gestión de Errores con Spring Boot
                throw new RuntimeException();
            }
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password Data Error");
        }

        return null;
    }


    @Operation(summary = "Delete my User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "No content",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = User.class))
                    )})
    })
    @Transactional
    @DeleteMapping("/user/")
    public ResponseEntity<?> deleteMyUser(@AuthenticationPrincipal User user){

            Optional<User> userUtil = userService.findById(user.getId());
            if (userUtil.isPresent()) {
                User userResponse = userUtil.get();

                if (userResponse.getPassword().equals(user.getPassword()))
                    userService.deleteById(user.getId());
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Operation(summary = "Get my User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User Found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = User.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "c0a8002c-867f-1b6f-8186-7f5c8ab70000",
                                                "username": "titintoro",
                                                "avatar": "https://avatar.com",
                                                "fullName": "Valentín Tola",
                                                "createdAt": "23/02/2023 18:40:16"
                                            }                                        \s
                                            """
                            )}
                    )})

    })
    @GetMapping("/me")
    public UserResponse getMyUser(@AuthenticationPrincipal User user){
       return UserResponse.fromUser(user);
    }


    @Operation(summary = "Delete other User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "No content",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = User.class))
                    )})
    })
    @Transactional
    @DeleteMapping("auth/user/{id}")
    public ResponseEntity<?> deleteOtherUser(@AuthenticationPrincipal User user, @PathVariable UUID id){
        if (user.getRoles().contains(UserRole.ADMIN)){
            userService.deleteById(id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
