package com.rapidshine.carwash.user_service.exceptions;

public class UserAlreadyException extends RuntimeException{
    public UserAlreadyException(String message){
        super(message);
    }
}
