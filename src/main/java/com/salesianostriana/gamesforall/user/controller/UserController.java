package com.salesianostriana.gamesforall.user.controller;

import com.salesianostriana.gamesforall.product.dto.BasicProductDTO;
import com.salesianostriana.gamesforall.product.dto.EasyProductDTO;
import com.salesianostriana.gamesforall.product.dto.PageDto;
import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.product.service.ProductService;
import com.salesianostriana.gamesforall.security.jwt.access.JwtProvider;
import com.salesianostriana.gamesforall.security.jwt.refresh.RefreshToken;
import com.salesianostriana.gamesforall.security.jwt.refresh.RefreshTokenException;
import com.salesianostriana.gamesforall.security.jwt.refresh.RefreshTokenRequest;
import com.salesianostriana.gamesforall.security.jwt.refresh.RefreshTokenService;
import com.salesianostriana.gamesforall.user.dto.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final ProductService productService;


    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> createUserWithUserRole(@RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUserWithUserRole(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }

    // Más adelante podemos manejar la seguridad de acceso a esta petición

    @PostMapping("/auth/register/admin")
    public ResponseEntity<UserResponse> createUserWithAdminRole(@RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUserWithAdminRole(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }


//    @Transactional
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
    //SE PUEDE OPTIMIZAR EÑ CÓDIGO PASÁNDOLO AL SERVICIO



    @Operation(summary = "Obtiene todos los productos de forma paginada del usuario autenticado")
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
    @GetMapping("/myproducts")
    public PageDto<EasyProductDTO> getUserProducts(@AuthenticationPrincipal User loggedUser, Pageable pageable) {

        Page<EasyProductDTO> productspaged =  userService.getUserProducts(loggedUser.getId(),pageable);

        return  new PageDto<>(productspaged);

    }

    @Operation(summary = "Obtiene todos los productos favoritos de forma paginada del usuario registrado")
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
    @GetMapping("/favorites")
    public PageDto<EasyProductDTO> getUserFavorites(@AuthenticationPrincipal User loggedUser, Pageable pageable) {

        Page<EasyProductDTO> productspaged =  userService.getUserFavoriteProducts(loggedUser.getId(),pageable);

       return  new PageDto<>(productspaged);

    }



    @Operation(summary = "Se crea un nuevo favorito en la lista de favoritos del usuario logeado a partir de un producto dado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado el favorito",
                    content = {}),
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
    @PostMapping("/favorites/{id}")
    public void addToFavorites (@PathVariable Long id, @AuthenticationPrincipal User user){
        productService.addProductToFavorites(user.getId(), id);
    }


    @Operation(summary = "Borra un favorito dado del usuario registrado a partir de un id dado")
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
    @DeleteMapping("/favorites/{id}")
    public void removeFromFavorites(@PathVariable Long id, @AuthenticationPrincipal User user){
        productService.removeProductFromFavorites(user.getId(), id);
    }



    @GetMapping("/me")
    public UserResponse getUser (@AuthenticationPrincipal User loggedUser, HttpServletRequest request){
        String authToken = request.getHeader("Authorization");
        System.out.println("User token: " + authToken);
            return UserResponse.fromUser(loggedUser);

    }
}
