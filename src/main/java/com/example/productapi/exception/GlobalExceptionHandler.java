
package com.example.productapi.exception;

import com.example.productapi.dto.ErrorResponse;
import com.example.productapi.model.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Environment env;

    @Autowired
    public GlobalExceptionHandler(Environment env) {
        this.env = env;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Product Not Found", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ErrorResponse.class)
    public ResponseEntity<Map<String, Object>> handleErrorResponse(ErrorResponse ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", ex.getStatus());
        response.put("error", ex.getError());
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getStatus()));
    }

    @ExceptionHandler(ServerWebInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleServerWebInput(ServerWebInputException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Invalid request body");

        Throwable rootCause = findRootCause(ex);

        if (rootCause instanceof IllegalArgumentException illegalArgumentException) {
            String detailMessage = illegalArgumentException.getMessage();
            if (detailMessage != null && detailMessage.contains("Unexpected value")) {
                String unexpectedValue = extractUnexpectedValue(detailMessage);
                response.put("details", "The value '" + unexpectedValue + "' is not valid.");
                response.put("validValues", Arrays.stream(ProductRequest.StatusEnum.values())
                        .map(ProductRequest.StatusEnum::getValue)
                        .toList());
            } else {
                response.put("details", "Error decoding the JSON: " + detailMessage);
            }
        } else {
            response.put("details", "There was an error processing the request: " +
                    (rootCause != null ? rootCause.getMessage() : "Unknown error"));
        }

        return ResponseEntity.badRequest().body(response);
    }

    private Throwable findRootCause(Throwable throwable) {
        while (throwable.getCause() != null && throwable != throwable.getCause()) {
            throwable = throwable.getCause();
        }
        return throwable;
    }

    private String extractUnexpectedValue(String message) {
        try {
            int startIndex = message.indexOf("Unexpected value") + "Unexpected value".length();
            int endIndex = message.indexOf("'", startIndex);
            return message.substring(startIndex, endIndex).trim();
        } catch (Exception e) {
            return "unknown";
        }
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Map<String, String>>> handleGenericException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Bad Request");
        errorResponse.put("message", ex.getMessage());
        return Mono.just(ResponseEntity.badRequest().body(errorResponse));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
