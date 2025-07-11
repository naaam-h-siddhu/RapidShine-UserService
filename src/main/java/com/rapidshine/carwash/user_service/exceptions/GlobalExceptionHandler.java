package com.rapidshine.carwash.user_service.exceptions;


import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialException(InvalidCredentialException e){
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getMessage(),
                LocalDateTime.now(),HttpStatus.UNAUTHORIZED.value()));
    }

    @ExceptionHandler(UserAlreadyException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyException(UserAlreadyException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(),LocalDateTime.now(),HttpStatus.NOT_FOUND.value()))  ;
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage(),
                LocalDateTime.now(),HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(InvalidJwtTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJwtTokenException(InvalidJwtTokenException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage(),
                LocalDateTime.now(),HttpStatus.NOT_FOUND.value()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleGeneralExcpeiton(MethodArgumentNotValidException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getBindingResult().getFieldError().getDefaultMessage(),
                LocalDateTime.now(),HttpStatus.INTERNAL_SERVER_ERROR    .value()));
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeExcpeiton(RuntimeException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage(),
                LocalDateTime.now(),HttpStatus.INTERNAL_SERVER_ERROR    .value()));
    }

}
