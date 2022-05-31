package com.north.poc.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleItemNotFoundException(
            NotFoundException notFoundException,
            WebRequest request
    ){
        log.info(notFoundException.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<Object> handleItemNotFoundException(
            InvalidCredentialException invalidCredentialException,
            WebRequest request
    ){
        log.info(invalidCredentialException.getMessage());
        return ResponseEntity.badRequest().build();
    }
}
