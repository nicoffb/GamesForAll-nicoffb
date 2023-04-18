package com.salesianostriana.gamesforall.message.controller;


import com.salesianostriana.gamesforall.message.model.Message;
import com.salesianostriana.gamesforall.message.model.MessagePK;
import com.salesianostriana.gamesforall.message.service.MessageService;
import com.salesianostriana.gamesforall.product.dto.PageDto;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;


    @Operation(summary = "Obtiene todos los mensajes de un usuario de forma paginada a partir de su uuid")
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
    public PageDto<MessageDTO> getByCriteria(@PathVariable UUID userId, @PageableDefault(size = 3, page = 0) Pageable pageable) {

        Page<MessageDTO> messageList = messageService.findMessagesById(userId,pageable).map(m-> MessageDTO.of(m));

        return new PageDto<>(messageList);

    }


    @Operation(summary = "Se crea una valoración del usuario registrado hacia el usuario dado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado la valoración",
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
    public ResponseEntity<MessageDTO> createValoration (@RequestBody MessageRequestDTO created, @AuthenticationPrincipal User user, @PathVariable UUID targetUser){

        Message message = created.toMessage(created);

        MessagePK pk = new MessagePK(user.getId(),targetUser);
        message.setPK(pk);

        message.setEmisorUser(user);
        message.setReceptorUser(userService.findById(targetUser));

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