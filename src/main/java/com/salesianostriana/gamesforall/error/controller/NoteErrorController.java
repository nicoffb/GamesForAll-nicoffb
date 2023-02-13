package com.salesianostriana.gamesforall.error.controller;


import com.salesianostriana.gamesforall.error.model.ApiError;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(NoteErrorController.ERROR_PATH)
public class NoteErrorController extends AbstractErrorController {

    static final String ERROR_PATH = "/error";

    public String getErrorPath() {
        return ERROR_PATH;
    }

    public NoteErrorController(ErrorAttributes errorAttributes)  {
        // De esta forma, no le permitimos añadir ningún ViewResolver que pueda hacer renderizar una página HTML.
        super(errorAttributes, Collections.emptyList());
    }

   @RequestMapping
    public ResponseEntity<ApiError> error(HttpServletRequest request) {
        Map<String, Object> mapErrorAttributes = this.getErrorAttributes(request,
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.BINDING_ERRORS, ErrorAttributeOptions.Include.EXCEPTION, ErrorAttributeOptions.Include.MESSAGE, ErrorAttributeOptions.Include.STACK_TRACE));
        ApiError apiError = ApiError.fromErrorAttributes(mapErrorAttributes);
        HttpStatus status = this.getStatus(request);
        return ResponseEntity.status(status).body(apiError);
    }


}


