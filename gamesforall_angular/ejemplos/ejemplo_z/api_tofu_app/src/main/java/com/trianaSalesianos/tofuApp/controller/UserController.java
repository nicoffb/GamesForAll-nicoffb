package com.trianaSalesianos.tofuApp.controller;

import com.trianaSalesianos.tofuApp.exception.UserNotFoundException;
import com.trianaSalesianos.tofuApp.model.User;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientResponse;
import com.trianaSalesianos.tofuApp.model.dto.page.PageDto;
import com.trianaSalesianos.tofuApp.model.dto.user.*;
import com.trianaSalesianos.tofuApp.security.jwt.JwtProvider;
import com.trianaSalesianos.tofuApp.security.refresh.RefreshToken;
import com.trianaSalesianos.tofuApp.security.refresh.RefreshTokenException;
import com.trianaSalesianos.tofuApp.security.refresh.RefreshTokenRequest;
import com.trianaSalesianos.tofuApp.security.refresh.RefreshTokenService;
import com.trianaSalesianos.tofuApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Tag(name= "Users", description = "Users controllers")
@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")

public class UserController {
    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Operation(summary = "Get all the ingredients, can use search parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "All users fetched succesfully",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                                 "content": [
                                                                     {
                                                                         "id": "ac132001-865b-1a5b-8186-5b5bba320000",
                                                                         "username": "ADamas",
                                                                         "avatar": "peepoSad3.jpg",
                                                                         "fullname": "Alejandro Damas",
                                                                         "email": "damas.viale23@triana.salesianos.edu",
                                                                         "description": "Usuario admin para controlarlo todo y tal",
                                                                         "createdAt": "16/02/2023 18:53:03"
                                                                     },
                                                                     {
                                                                         "id": "ac132001-865b-1a5b-8186-5b5bc7710001",
                                                                         "username": "AleUser",
                                                                         "avatar": "peepoSad3.jpg",
                                                                         "fullname": "Alejandro Damas",
                                                                         "email": "alejandrodamas3d@gmail.com",
                                                                         "description": "Usuario user para hacer pruebas y tal",
                                                                         "createdAt": "16/02/2023 18:53:06"
                                                                     }
                                                                 ],
                                                                 "last": true,
                                                                 "first": true,
                                                                 "totalPages": 1,
                                                                 "totalElements": 2
                                                             }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "Users not found",
                    content = @Content),
    })
    @GetMapping("/user")
    public PageDto<UserResponse> getAll(
            @Parameter(description = "Can be used to search users by their variables")
            @RequestParam(value = "search", defaultValue = "") String search,
                                        @PageableDefault(size = 10, page = 0) Pageable pageable){
        return userService.getAllBySearch(search,pageable);
    }

    @Operation(summary = "Get an user by its username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User fetched succesfully",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserDetailsResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "username": "ADamas",
                                                    "avatar": "peepoSad3.jpg",
                                                    "fullname": "Alejandro Damas",
                                                    "email": "damas.viale23@triana.salesianos.edu",
                                                    "description": "Usuario admin para controlarlo todo y tal",
                                                    "birthday": "10/01/1998",
                                                    "recipes": [
                                                        {
                                                            "id": "ac132001-865c-1a80-8186-5c9b088d0001",
                                                            "name": "Licensed Soft Pizza",
                                                            "description": "systems Legacy Cambridgeshire platforms ROI",
                                                            "category": "Vegan",
                                                            "img": "default_recipe.jpg",
                                                            "author": "Alejandro Damas",
                                                            "prepTime": 20,
                                                            "createdAt": "17/02/2023 00:41:49"
                                                        },
                                                        {
                                                            "id": "ac132001-865c-1a80-8186-5c9b147c0002",
                                                            "name": "Rustic Fresh Chair",
                                                            "description": "bypassing AI Customer Tuna",
                                                            "category": "Standard",
                                                            "img": "default_recipe.jpg",
                                                            "author": "Alejandro Damas",
                                                            "prepTime": 30,
                                                            "createdAt": "17/02/2023 00:41:52"
                                                        },
                                                        {
                                                            "id": "ac132001-865c-1a80-8186-5c9b1f370003",
                                                            "name": "Handcrafted Soft Salad",
                                                            "description": "firewall Borders Outdoors",
                                                            "category": "Low-fats",
                                                            "img": "default_recipe.jpg",
                                                            "author": "Alejandro Damas",
                                                            "prepTime": 40,
                                                            "createdAt": "17/02/2023 00:41:55"
                                                        },
                                                        {
                                                            "id": "ac132001-865c-1a80-8186-5c9b30f50004",
                                                            "name": "Sleek Soft Chips",
                                                            "description": "Tactics Advanced Licensed protocol",
                                                            "category": "Low-carbs",
                                                            "img": "default_recipe.jpg",
                                                            "author": "Alejandro Damas",
                                                            "prepTime": 30,
                                                            "createdAt": "17/02/2023 00:41:59"
                                                        },
                                                        {
                                                            "id": "ac132001-865c-1a80-8186-5c9afcbe0000",
                                                            "name": "Intelligent Fresh Mouse",
                                                            "description": "Tools monetize",
                                                            "category": "Vegetarian",
                                                            "img": "default_recipe.jpg",
                                                            "author": "Alejandro Damas",
                                                            "prepTime": 50,
                                                            "createdAt": "17/02/2023 00:41:46"
                                                        }
                                                    ],
                                                    "favorites": []
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content),
    })
    @GetMapping("/user/{username}")
    public UserDetailsResponse getUserByUsername(
            @Parameter(description = "Username of the user to get")
            @PathVariable String username){
        return userService.getByUsername(username);
    }

    @Operation(summary = "Get the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User fetched succesfully",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserDetailsResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "username": "ADamas",
                                                    "avatar": "peepoSad3.jpg",
                                                    "fullname": "Alejandro Damas",
                                                    "email": "damas.viale23@triana.salesianos.edu",
                                                    "description": "Usuario admin para controlarlo todo y tal",
                                                    "birthday": "10/01/1998",
                                                    "recipes": [
                                                        {
                                                            "id": "ac132001-865c-1a80-8186-5c9b088d0001",
                                                            "name": "Licensed Soft Pizza",
                                                            "description": "systems Legacy Cambridgeshire platforms ROI",
                                                            "category": "Vegan",
                                                            "img": "default_recipe.jpg",
                                                            "author": "Alejandro Damas",
                                                            "prepTime": 20,
                                                            "createdAt": "17/02/2023 00:41:49"
                                                        },
                                                        {
                                                            "id": "ac132001-865c-1a80-8186-5c9b147c0002",
                                                            "name": "Rustic Fresh Chair",
                                                            "description": "bypassing AI Customer Tuna",
                                                            "category": "Standard",
                                                            "img": "default_recipe.jpg",
                                                            "author": "Alejandro Damas",
                                                            "prepTime": 30,
                                                            "createdAt": "17/02/2023 00:41:52"
                                                        },
                                                        {
                                                            "id": "ac132001-865c-1a80-8186-5c9b1f370003",
                                                            "name": "Handcrafted Soft Salad",
                                                            "description": "firewall Borders Outdoors",
                                                            "category": "Low-fats",
                                                            "img": "default_recipe.jpg",
                                                            "author": "Alejandro Damas",
                                                            "prepTime": 40,
                                                            "createdAt": "17/02/2023 00:41:55"
                                                        },
                                                        {
                                                            "id": "ac132001-865c-1a80-8186-5c9b30f50004",
                                                            "name": "Sleek Soft Chips",
                                                            "description": "Tactics Advanced Licensed protocol",
                                                            "category": "Low-carbs",
                                                            "img": "default_recipe.jpg",
                                                            "author": "Alejandro Damas",
                                                            "prepTime": 30,
                                                            "createdAt": "17/02/2023 00:41:59"
                                                        },
                                                        {
                                                            "id": "ac132001-865c-1a80-8186-5c9afcbe0000",
                                                            "name": "Intelligent Fresh Mouse",
                                                            "description": "Tools monetize",
                                                            "category": "Vegetarian",
                                                            "img": "default_recipe.jpg",
                                                            "author": "Alejandro Damas",
                                                            "prepTime": 50,
                                                            "createdAt": "17/02/2023 00:41:46"
                                                        }
                                                    ],
                                                    "favorites": []
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content),
    })
    @GetMapping("/user/me")
    public UserDetailsResponse getCurrentUserProfile(@AuthenticationPrincipal User user){
        return userService.getByUsername(user.getUsername());
    }

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "User created succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": "ac111001-8680-1a98-8186-80618ef60000",
                                                    "username": "Serenity_Willms4",
                                                    "avatar": "default_user_avatar.png",
                                                    "fullname": "Jason Altenwerth",
                                                    "email": "Mary_Bednar73@hotmail.com",
                                                    "description": "",
                                                    "createdAt": "23/02/2023 23:25:22"
                                                }                                                                     
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "There was an error with the data provided",
                    content = @Content),
    })
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> createUserWithUserRole(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Values needed to create a user")
            @Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUserWithUserRole(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }

    @Operation(summary = "Creates a new user with role admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Admin created succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": "ac111001-8680-1a98-8186-80629eb90001",
                                                    "username": "Lauren_Senger23",
                                                    "avatar": "default_user_avatar.png",
                                                    "fullname": "Miss Darren Ruecker",
                                                    "email": "Ilene.Stokes@hotmail.com",
                                                    "description": "",
                                                    "createdAt": "23/02/2023 23:26:32"
                                                }                                                                    
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "There was an error with the data provided",
                    content = @Content),
    })
    @PostMapping("/auth/register/admin")
    public ResponseEntity<UserResponse> createUserWithAdminRole(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Values needed to create a user")
            @Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUserWithAdminRole(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }


    @Operation(summary = "Let the user login, and receives a token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "User logged succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtUserResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "username": "ADamas",
                                                    "avatar": "peepoSad3.jpg",
                                                    "fullname": "Alejandro Damas",
                                                    "email": "damas.viale23@triana.salesianos.edu",
                                                    "createdAt": "16/02/2023 18:53:03",
                                                    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhYzEzMjAwMS04NjViLTFhNWItODE4Ni01YjViYmEzMjAwMDAiLCJpYXQiOjE2NzcxOTEyNzcsImV4cCI6MTY3NzE5MjE3N30.8u6bWqGlfZVv89cyXwQ3oB5_jp6Zbe9CeoDQQ1_FhRCv4eICfiuqEmdj6CmaVkx4CbdQ4doV4vizsYPygcOBzA",
                                                    "refreshToken": "0b046be0-3c77-4167-9b6b-4d2f6e976574"
                                                }                                                        
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "There was an error with the data provided",
                    content = @Content),
    })
    @PostMapping("/auth/login")
    public ResponseEntity<JwtUserResponse> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Username and password needed to login")
            @Valid @RequestBody LoginRequest loginRequest) {

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

    @Operation(summary = "Edit the password of the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Password edited succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": "ac132001-865b-1a5b-8186-5b5bba320000",
                                                    "username": "ADamas",
                                                    "avatar": "peepoSad3.jpg",
                                                    "fullname": "Alejandro Damas",
                                                    "email": "damas.viale23@triana.salesianos.edu",
                                                    "description": "Usuario admin para controlarlo todo y tal",
                                                    "createdAt": "16/02/2023 18:53:03"
                                                }                                                                       
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "There was an error with the data provided",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content),

    })
    @PutMapping("/user/changepassword")
    public UserResponse changePassword(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Values needed to change the password")
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest,
                                                       @AuthenticationPrincipal User loggedUser) {
        return userService.changePassword(changePasswordRequest,loggedUser);
    }

    @Operation(summary = "Logout of the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "LoggedOut edited succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": "ac132001-865b-1a5b-8186-5b5bba320000",
                                                    "username": "ADamas",
                                                    "avatar": "peepoSad3.jpg",
                                                    "fullname": "Alejandro Damas",
                                                    "email": "damas.viale23@triana.salesianos.edu",
                                                    "description": "Usuario admin para controlarlo todo y tal",
                                                    "createdAt": "16/02/2023 18:53:03"
                                                }                                                                       
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "There was an error with the data provided",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content),

    })
    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal User user){
        refreshTokenService.deleteByUser(user);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Edit values of the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User edited succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": "ac132001-865b-1a5b-8186-5b5bba320000",
                                                    "username": "ADamas",
                                                    "avatar": "peepoSad3.jpg",
                                                    "fullname": "Alejandro Damas",
                                                    "email": "damas.viale23@triana.salesianos.edu",
                                                    "description": "Usuario admin para controlarlo todo y tal",
                                                    "createdAt": "16/02/2023 18:53:03"
                                                }                                                                       
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "There was an error with the data provided",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content),

    })
    @PutMapping("/user/edit")
    public UserResponse editUser(@AuthenticationPrincipal User user,
                                 @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Values needed to edit the current user")
                                 @Valid @RequestBody EditUserRequest editUserRequest){
        return userService.editUser(user,editUserRequest);
    }

    @Operation(summary = "Edit values of a user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User edited succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": "ac132001-865b-1a5b-8186-5b5bba320000",
                                                    "username": "ADamas",
                                                    "avatar": "peepoSad3.jpg",
                                                    "fullname": "Alejandro Damas",
                                                    "email": "damas.viale23@triana.salesianos.edu",
                                                    "description": "Usuario admin para controlarlo todo y tal",
                                                    "createdAt": "16/02/2023 18:53:03"
                                                }                                                                       
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "There was an error with the data provided",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content),

    })
    @PutMapping("/user/edit/{username}")
    public UserResponse editUserById(
            @Parameter(description = "Username of the current user")
            @PathVariable String username,
                                     @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Values needed to edit the user")
                                     @Valid @RequestBody EditUserRequest editUserRequest){
        return userService.editUser(username ,editUserRequest);
    }

    @Operation(summary = "Change the avatar of the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Avatar edited succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "username": "ADamas",
                                                    "avatar": "koalaTired.jpg",
                                                    "fullname": "Alejandro Damas"
                                                }                                                                    
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "There was an error with the data provided",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content),

    })
    @PutMapping("/user/changeavatar")
    public UserResponse changeAvatar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New avatar")
            @RequestPart("file") MultipartFile file,
                                          @AuthenticationPrincipal User user){
        return userService.changeAvatar(file, user);
    }

    @Operation(summary = "Create a new refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Refresh token created succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtUserResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhYzEzMjAwMS04NjViLTFhNWItODE4Ni01YjViYmEzMjAwMDAiLCJpYXQiOjE2NzcxOTIwMTIsImV4cCI6MTY3NzE5MjkxMn0.UyzdLxhtFJkWlVjJFm3v7yonVxNVamGNkj3Os4Eg13os04g8bMX8IIc6hZKj9kYzHoqbSXQQCl3pMsbM7ungvw",
                                                    "refreshToken": "44f3d6d3-dede-4b25-bff9-c4ce560bf3e0"
                                                }                                                                     
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "Refresh token not found",
                    content = @Content),
    })
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Current token of the user")
            @RequestBody RefreshTokenRequest refreshTokenRequest) {
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
    @DeleteMapping("/user/{username}")
    public ResponseEntity<?> removeUser(@PathVariable String username){
            User user = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException());
            userService.delete(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @DeleteMapping("/user/me")
    public ResponseEntity<?> removeAuthenticatedUser(
            @AuthenticationPrincipal User user){
        userService.delete(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/user/follow/{username}")
    public UserDetailsResponse followUser(
            @PathVariable String username,
            @AuthenticationPrincipal User user){
        return userService.followUser(user, username);
    }
}
