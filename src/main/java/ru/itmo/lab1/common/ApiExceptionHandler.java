package ru.itmo.lab1.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.itmo.lab1.auth.InvalidCredentialsException;
import ru.itmo.lab1.note.CurrentUserNotFoundException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> fields = new LinkedHashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            String message = fieldError.getDefaultMessage() == null
                    ? "invalid value"
                    : fieldError.getDefaultMessage();
            fields.putIfAbsent(fieldError.getField(), message);
        }

        Map<String, Object> body = errorBody(HttpStatus.BAD_REQUEST, "Validation failed");
        body.put("fields", fields);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleMalformedJson() {
        return response(HttpStatus.BAD_REQUEST, "Malformed request");
    }

    @ExceptionHandler({InvalidCredentialsException.class, CurrentUserNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleUnauthorized() {
        return response(HttpStatus.UNAUTHORIZED, "Unauthorized");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleUnexpected(Exception exception) {
        if (exception instanceof ErrorResponse errorResponse) {
            HttpStatusCode status = errorResponse.getStatusCode();
            HttpStatus httpStatus = HttpStatus.resolve(status.value());
            String message = httpStatus == null ? "Request failed" : httpStatus.getReasonPhrase();
            return response(status, message);
        }

        return response(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    }

    private ResponseEntity<Map<String, Object>> response(HttpStatusCode status, String error) {
        return ResponseEntity.status(status).body(errorBody(status, error));
    }

    private Map<String, Object> errorBody(HttpStatusCode status, String error) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("error", error);
        return body;
    }
}
