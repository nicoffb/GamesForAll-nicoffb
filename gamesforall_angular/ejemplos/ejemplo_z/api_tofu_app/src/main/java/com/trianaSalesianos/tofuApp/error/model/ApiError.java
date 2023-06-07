package com.trianaSalesianos.tofuApp.error.model;

import com.trianaSalesianos.tofuApp.error.model.impl.ApiErrorImpl;
import com.trianaSalesianos.tofuApp.error.model.impl.ApiValidationSubError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ApiError {
    static ApiError fromErrorAttributes(Map<String, Object> defaultErrorAttributesMap) {

        //int statusCode = ((Integer)defaultErrorAttributesMap.get("status")).intValue();

        int statusCode = -1;
        HttpStatus status = null;

        if (defaultErrorAttributesMap.containsKey("status")) {
            if (defaultErrorAttributesMap.get("status") instanceof Integer) {
                statusCode = ((Integer)defaultErrorAttributesMap.get("status")).intValue();
                status = HttpStatus.valueOf(statusCode);
            } else if (defaultErrorAttributesMap.get("status") instanceof String) {
                status = HttpStatus.valueOf((String) defaultErrorAttributesMap.get("status"));
                statusCode = status.value();
            }
        }



        ApiErrorImpl result =
                ApiErrorImpl.builder()
                        //.status(HttpStatus.valueOf(statusCode))
                        .status(status)
                        .statusCode(statusCode)
                        .message((String) defaultErrorAttributesMap.getOrDefault("message", "No message available"))
                        .path((String) defaultErrorAttributesMap.getOrDefault("path", "No path available"))
                        .build();

        if (defaultErrorAttributesMap.containsKey("errors")) {

            List<ObjectError> errors = (List<ObjectError>) defaultErrorAttributesMap.get("errors");

            List<ApiSubError> subErrors = errors.stream()
                    .map(ApiValidationSubError::fromObjectError)
                    .collect(Collectors.toList());

            result.setSubErrors(subErrors);

        }

        return result;
    }

    HttpStatus getStatus();
    int getStatusCode();
    String getMessage();
    String getPath();
    LocalDateTime getDate();
    List<ApiSubError> getSubErrors();
}
