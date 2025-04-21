package org.conference_desks.globalExceptionHandler;


import org.conference_desks.common.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageService messageService;

    public GlobalExceptionHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Map<String, Object>> handleApiException(ApiException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getStatus(), ex.getMessageKey(), request.getRequestURI(), ex.getErrorCode().name(), ex.getArgs());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
        return buildRawMessageResponse(HttpStatus.valueOf(ex.getStatusCode().value()), ex.getReason(), request.getRequestURI(), null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        return buildErrorResponse(ErrorCode.INVALID_ARGUMENT, request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex, HttpServletRequest request) {
       ex.printStackTrace();
        return buildErrorResponse(ErrorCode.INTERNAL_ERROR, request.getRequestURI(), ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String messageKey, String path, String errorCode, Object... args) {
        String localizedMessage = messageService.getMessage(messageKey, args);
        return buildRawMessageResponse(status, localizedMessage, path, errorCode);
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(ErrorCode errorCode, String path, Object... args) {
        return buildErrorResponse(errorCode.getStatus(), errorCode.getMessageKey(), path, errorCode.name(), args);
    }
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            org.springframework.web.bind.MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .findFirst()
                .orElse("Błąd walidacji");

        return buildRawMessageResponse(HttpStatus.BAD_REQUEST, errorMessage, request.getRequestURI(),null);
    }

    private ResponseEntity<Map<String, Object>> buildRawMessageResponse(HttpStatus status, String message, String path, String errorCode) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", status.value());
        errorBody.put("error", message);
        errorBody.put("path", path);
        if (errorCode != null) {
            errorBody.put("errorCode", errorCode);
        }
        return ResponseEntity.status(status).body(errorBody);
    }

}
