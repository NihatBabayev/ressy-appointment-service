package com.example.ressyappointmentservice.exception;

public class AppointmentNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "No such appointment is found";
    public AppointmentNotFoundException(){
        super(DEFAULT_MESSAGE);
    }
}
