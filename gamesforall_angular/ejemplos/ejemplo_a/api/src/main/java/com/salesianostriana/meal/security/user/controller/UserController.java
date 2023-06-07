package com.salesianostriana.meal.security.user.controller;

import com.salesianostriana.meal.model.dto.plato.PlatoResponseDTO;
import com.salesianostriana.meal.security.jwt.access.JwtProvider;
import com.salesianostriana.meal.security.jwt.refresh.RefreshToken;
import com.salesianostriana.meal.security.jwt.refresh.RefreshTokenException;
import com.salesianostriana.meal.security.jwt.refresh.RefreshTokenRequest;
import com.salesianostriana.meal.security.jwt.refresh.RefreshTokenService;
import com.salesianostriana.meal.security.user.User;
import com.salesianostriana.meal.security.user.dto.*;
import com.salesianostriana.meal.security.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
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
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;

    private final RefreshTokenService refreshTokenService;


    @Operation(summary = "Registra al usuario en la api como cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha registrado correctamente",
                    content = {@Content(schema = @Schema(implementation = JwtUserResponse.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Los datos no son válidos",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> createUserWithUserRole(@RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUserWithUserRole(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }

    @Operation(summary = "Registra un usuario en la api como administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha registrado correctamente",
                    content = {@Content(schema = @Schema(implementation = JwtUserResponse.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Los datos no son válidos",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "403",
                    description = "El usuario actual no tiene rol de administrador",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @PostMapping("/auth/register/admin")
    public ResponseEntity<UserResponse> createUserWithAdminRole(@RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUserWithAdminRole(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }

    @Operation(summary = "Registra al usuario en la api como propietario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha registrado correctamente",
                    content = {@Content(schema = @Schema(implementation = JwtUserResponse.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Los datos no son válidos",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @PostMapping("/auth/register/owner")
    public ResponseEntity<UserResponse> createUserWithOwnerRole(@RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUserWithOwnerRole(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }

    @Operation(summary = "Log in en la api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha logado correctamente",
                    content = {@Content(schema = @Schema(implementation = JwtUserResponse.class))}),
            @ApiResponse(responseCode = "401",
                    description = "Las credenciales están mal",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @PostMapping("/auth/login")
    @Transactional
    public ResponseEntity<JwtUserResponse> login(@RequestBody LoginRequest loginRequest) {

        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        User user = (User) authentication.getPrincipal();

        refreshTokenService.deleteByUser(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JwtUserResponse.of(user, token, refreshToken.getToken()));

    }


    @Operation(summary = "Cambia la contraseña del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha cambiado la contraseña",
                    content = {@Content(schema = @Schema(implementation = PlatoResponseDTO.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el usuario en la base de datos",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "401",
                    description = "No se está loggeado",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @PutMapping("/user/changePassword")
    public UserResponse changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
                                                       @AuthenticationPrincipal User loggedUser) {
        return UserResponse.fromUser(userService.editPassword(loggedUser, changePasswordRequest));
    }

    @Operation(summary = "Borra la cuenta de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la cuenta con éxito",
                    content = {}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el usuario en la base de datos",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "400",
                    description = "No se ha permitido el borrado porque el usuario gestiona algún restaurante",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "401",
                    description = "No se está loggeado",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @Parameter(description = "Id del restaurante del cual borrar la imagen", name = "id", required = true)
    @DeleteMapping("/user/deleteAccount")
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal User loggedUser) {
        userService.delete(loggedUser);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtiene los detalles del usuario logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han conseguido los detalles del usuario",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class))
                    }),
            @ApiResponse(responseCode = "401",
                    description = "No se está loggeado",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @GetMapping("/me")
    public UserResponse profile(@AuthenticationPrincipal User loggedUser) {
        return UserResponse.fromUser(loggedUser);
    }

    @PostMapping("/refreshtoken")
    @Transactional
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

}
