package com.leojcl.todos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handlerException(Exception exc){
        return buildExceptionEntity(exc, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponse> handlerException(ResponseStatusException exc){
        return buildExceptionEntity(exc, HttpStatus.valueOf(exc.getStatusCode().value()));
    }

    private ResponseEntity<ExceptionResponse> buildExceptionEntity(Exception exc, HttpStatus status){
        ExceptionResponse error = new ExceptionResponse();
        error.setStatus(status.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, status);
    }
}
