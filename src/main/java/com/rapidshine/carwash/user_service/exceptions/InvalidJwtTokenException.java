package com.rapidshine.carwash.user_service.exceptions;

public class InvalidJwtTokenException extends RuntimeException{
    public InvalidJwtTokenException(String message) {
        super(message);

    }
}
