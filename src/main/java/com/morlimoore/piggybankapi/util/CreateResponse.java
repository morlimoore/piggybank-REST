package com.morlimoore.piggybankapi.util;

import com.morlimoore.piggybankapi.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public class CreateResponse {

    public static <T> ResponseEntity<ApiResponse<T>> createResponse(ApiResponse<T> response) {
        return new ResponseEntity<>(response, response.getStatus());
    }

    public static ResponseEntity<ApiResponse<String>> bindingResultError(BindingResult result) {
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.BAD_REQUEST);
        response.setMessage("Validation error");
        response.setDebugMessage(result.getFieldError().getDefaultMessage());
        response.setError(result.getFieldError().toString());
        response.addValidationErrors(result.getFieldErrors());
        return createResponse(response);
    }
}

