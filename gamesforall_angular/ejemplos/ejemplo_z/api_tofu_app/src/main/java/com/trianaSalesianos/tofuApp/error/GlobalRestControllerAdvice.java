package com.trianaSalesianos.tofuApp.error;

import com.trianaSalesianos.tofuApp.error.model.impl.ApiErrorImpl;
import com.trianaSalesianos.tofuApp.error.model.impl.ApiValidationSubError;
import com.trianaSalesianos.tofuApp.exception.*;
import com.trianaSalesianos.tofuApp.security.errorhandling.JwtTokenException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildApiError(ex.getMessage(), request, status);
    }


    @ExceptionHandler({
            UserNotFoundException.class,
            RecipeNotFoundException.class,
            IngredientNotFoundException.class,
            TypeNotFoundException.class,
            StepNotFoundException.class,
            IngredientNotFoundInRecipeException.class,
            DietDaysNotFoundException.class,
            CategoryNotFoundException.class,
    })
    public ResponseEntity<?> handleNotFoundException(EntityNotFoundException exception, WebRequest request) {
        return buildApiError(exception.getMessage(), request, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({
            PwDataErrorException.class,
            RecipeTypeInUse.class,

    })
    public ResponseEntity<?> handleBadRequestException(RuntimeException exception, WebRequest request) {
        return buildApiError(exception.getMessage(), request, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({
            RecipeAuthorNotValidException.class,
            IngredientAuthorNotValidException.class,
            DietUserNotValidException.class,
    })
    public ResponseEntity<?> handleForbiddenException(RuntimeException exception, WebRequest request) {
        return buildApiError(exception.getMessage(), request, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiErrorImpl.builder()
                                .status(HttpStatus.BAD_REQUEST)
                                .message("Constraint Validation error. Please check the sublist.")
                                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                                .subErrors(exception.getConstraintViolations().stream()
                                        .map(v -> {
                                            return ApiValidationSubError.builder()
                                                    .message(v.getMessage())
                                                    .rejectedValue(v.getInvalidValue())
                                                    .object(v.getRootBean().getClass().getSimpleName())
                                                    .field( ((PathImpl)v.getPropertyPath()).getLeafNode().asString())
                                                    .build();
                                        })
                                        .collect(Collectors.toList())
                                )
                                .build()
                );
    }



    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildApiErrorWithSubErrors("Validation error. Please check the sublist.", request, status, ex.getAllErrors());
    }

    private final ResponseEntity<Object> buildApiError(String message, WebRequest request, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(
                        ApiErrorImpl.builder()
                                .status(status)
                                .message(message)
                                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                                .build()
                );
    }

    private final ResponseEntity<Object> buildApiErrorWithSubErrors(String message, WebRequest request, HttpStatus status, List<ObjectError> subErrors) {
        return ResponseEntity
                .status(status)
                .body(
                        ApiErrorImpl.builder()
                                .status(status)
                                .message(message)
                                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                                .subErrors(subErrors.stream()
                                        .map(ApiValidationSubError::fromObjectError)
                                        .collect(Collectors.toList())
                                )
                                .build()
                );

    }
}