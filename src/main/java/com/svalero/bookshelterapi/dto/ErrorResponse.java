package com.svalero.bookshelterapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private int errorCode;
    private String message;
    private Map<String, String> errors;

    private ErrorResponse(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        message = errorMessage;
        errors = new HashMap<>();
    }

    public static ErrorResponse validationError(Map<String, String> errors) {
        return new ErrorResponse(104, "Validation error", errors);
    }

    public static ErrorResponse generalError(int code, String message) {
        return new ErrorResponse(code, message);
    }


}
