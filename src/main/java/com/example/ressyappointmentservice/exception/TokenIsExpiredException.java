package com.example.ressyappointmentservice.exception;

public class TokenIsExpiredException extends RuntimeException{
    private static final String DEFAULT_MESSAGE  = "Token Is Expired";
    public TokenIsExpiredException(){
        super(DEFAULT_MESSAGE);
    }
}
