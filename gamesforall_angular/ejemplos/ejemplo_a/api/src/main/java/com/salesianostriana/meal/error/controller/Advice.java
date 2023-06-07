package com.salesianostriana.meal.error.controller;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesianostriana.meal.error.exception.AlreadyRatedException;
import com.salesianostriana.meal.error.exception.BadRequestException;
import com.salesianostriana.meal.error.exception.InvalidPasswordException;
import com.salesianostriana.meal.error.exception.InvalidSearchException;
import com.salesianostriana.meal.error.model.Error;
import com.salesianostriana.meal.error.model.SubError;
import com.salesianostriana.meal.security.error.JwtTokenException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class Advice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({SecurityException.class})
    public ResponseEntity<?> handleInvalidSearch(SecurityException exception, WebRequest request){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Error.builder()
                .status(HttpStatus.FORBIDDEN)
                .code(HttpStatus.FORBIDDEN.value())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .message(exception.getMessage())
                .build());
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<?> handleInvalidSearch(BadRequestException exception, WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Error.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(HttpStatus.BAD_REQUEST.value())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .message(exception.getMessage())
                .build());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleNotFound(EntityNotFoundException exception, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Error.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .code(HttpStatus.NOT_FOUND.value())
                        .path(((ServletWebRequest) request).getRequest().getRequestURI())
                        .message(exception.getMessage())
                .build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Error.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(HttpStatus.BAD_REQUEST.value())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .message(ex.getMessage())
                        .subErrors(ex.getAllErrors().stream().map(SubError::fromObjectError).toList())
                .build());
    }

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header("WWW-Authenticate", "Bearer")
                .body(ErrorMessage.of(HttpStatus.UNAUTHORIZED, ex.getMessage(), request.getRequestURI()));

    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorMessage.of(HttpStatus.FORBIDDEN, ex.getMessage(), request.getRequestURI()));

    }


    @ExceptionHandler({JwtTokenException.class})
    public ResponseEntity<?> handleTokenException(JwtTokenException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorMessage.of(HttpStatus.FORBIDDEN, ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<?> handleUserNotExistsException(UsernameNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorMessage.of(
                        HttpStatus.UNAUTHORIZED,
                        ex.getMessage(),
                        request.getRequestURI()
                ));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class ErrorMessage {

        private HttpStatus status;
        private String message, path;

        @Builder.Default
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
        private LocalDateTime dateTime = LocalDateTime.now();

        public static ErrorMessage of (HttpStatus status, String message, String path) {
            return ErrorMessage.builder()
                    .status(status)
                    .message(message)
                    .path(path)
                    .build();
        }

    }
}
