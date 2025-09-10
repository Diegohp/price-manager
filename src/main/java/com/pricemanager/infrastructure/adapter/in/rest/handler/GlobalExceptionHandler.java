package com.pricemanager.infrastructure.adapter.in.rest.handler;

import com.pricemanager.infrastructure.adapter.in.rest.exception.MissingFieldsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom Exceptions handler to customize response creating JSON object structure
 * */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {

        Map<String, String> error = new HashMap<>();

        error.put("error", "Bad Request");
        error.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {

        Map<String, String> error = new HashMap<>();

        error.put("error", "Internal server error");
        error.put("message", ex.getMessage());

        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(MissingFieldsException.class)
    public ResponseEntity<Map<String, Object>> handleMissingFields(MissingFieldsException ex) {

        Map<String, Object> response = new HashMap<>();

        response.put("error", "Bad Request");
        response.put("message", "Missing required fields");
        response.put("fields", ex.getMissingFields());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {

        Map<String, String> error = new HashMap<>();

        String paramName = ex.getName();
        String requiredType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown";
        String value = ex.getValue() != null ? ex.getValue().toString() : "null";

        error.put("error", "Bad Request");
        error.put("message", String.format(
                "Invalid value '%s' for parameter '%s'. Expected type: %s.",
                value, paramName, requiredType
        ));

        return ResponseEntity.badRequest().body(error);
    }
}
