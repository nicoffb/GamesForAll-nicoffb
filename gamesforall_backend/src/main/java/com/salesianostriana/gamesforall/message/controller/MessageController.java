package com.salesianostriana.gamesforall.message.controller;


import com.salesianostriana.gamesforall.message.model.Message;

import com.salesianostriana.gamesforall.message.service.MessageService;

import com.salesianostriana.gamesforall.user.model.User;
import com.salesianostriana.gamesforall.user.service.UserService;
import com.salesianostriana.gamesforall.message.dto.MessageDTO;
import com.salesianostriana.gamesforall.message.dto.MessageRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;


    @Operation(summary = "Obtiene todos los mensajes de un usuario a partir de su uuid")
    @PageableAsQueryParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los mensajes",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = MessageDTO.class)))
                    }),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el usuario",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.gamesforall.exception.UserNotFoundException.class))),
            @ApiResponse(responseCode = "400",
                    description = "La búsqueda es incorrecta",
                    content = @Content)
    })
    @GetMapping("/search/{userId}")
    public List<MessageDTO> getMessagesById(@PathVariable UUID userId) {

        List<MessageDTO> messageList = messageService.findMessagesById(userId)
                .stream()
                .map(MessageDTO::of)
                .toList();

        return (messageList);

    }

    @Operation(summary = "Se obtienen los mensajes que tiene el usuario logado con el usuario objetivo")
    @PageableAsQueryParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los mensajes",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = MessageDTO.class)))
                    }),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el usuario",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.gamesforall.exception.UserNotFoundException.class))),
            @ApiResponse(responseCode = "400",
                    description = "La búsqueda es incorrecta",
                    content = @Content)
    })
    @GetMapping("/{userId}")
    public List<MessageDTO> getMessagesConversation( @AuthenticationPrincipal User loggedUser ,@PathVariable UUID userId) {

        List<MessageDTO> messageList = messageService.findChatMessages(loggedUser.getId(),userId)
                .stream()
                .map(MessageDTO::of)
                .toList();

        return (messageList);

    }


    @Operation(summary = "Se crea un mensaje desde el usuario logado hacia el usuario dado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado el mensaje",
                    content = {@Content(schema = @Schema(implementation = MessageDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Los datos no son válidos",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el usuario",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.gamesforall.exception.UserNotFoundException.class))),
            @ApiResponse(responseCode = "401",
                    description = "Full authentication is required to access this resource",
                    content = @Content)
    })
    @PostMapping("/{targetUser}")
    public ResponseEntity<MessageDTO> createMessage (@RequestBody MessageRequestDTO created, @AuthenticationPrincipal User user, @PathVariable UUID targetUser){

        Message message = created.toMessage(created);


        message.setEmisorUser(user);
        message.setReceptorUser(userService.findById(targetUser));

        message.setMessage_date(LocalDateTime.now());

        Message saved = messageService.add(message);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(message.getId()).toUri();

        MessageDTO converted = MessageDTO.of(saved);


        return ResponseEntity
                .created(createdURI)
                .body(converted);
    }


}
