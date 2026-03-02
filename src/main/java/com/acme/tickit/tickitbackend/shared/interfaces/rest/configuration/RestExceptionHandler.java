package com.acme.tickit.tickitbackend.shared.interfaces.rest.configuration;

import com.acme.tickit.tickitbackend.shared.interfaces.rest.resources.MessageResource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@RestControllerAdvice
public class RestExceptionHandler {

    private static final String SIGN_UP_MULTIPART_MESSAGE =
            "Use Content-Type: multipart/form-data with two parts: 'user' (application/json, required) and 'profileImage' (file, optional). "
                    + "Do not send the request as application/octet-stream. Use Postman or cURL with form-data.";

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<MessageResource> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        if (request.getRequestURI() != null && request.getRequestURI().contains("sign-up")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResource(SIGN_UP_MULTIPART_MESSAGE));
        }
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(new MessageResource(ex.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<MessageResource> handleMissingServletRequestPart(
            MissingServletRequestPartException ex, HttpServletRequest request) {
        if (request.getRequestURI() != null && request.getRequestURI().contains("sign-up")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResource(SIGN_UP_MULTIPART_MESSAGE));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MessageResource(ex.getMessage()));
    }
}
