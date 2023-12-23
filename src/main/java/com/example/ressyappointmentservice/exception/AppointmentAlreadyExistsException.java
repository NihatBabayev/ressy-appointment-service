package com.example.ressyappointmentservice.exception;

public class AppointmentAlreadyExistsException extends RuntimeException{
    private static final String DEFAULT_MESSAGE =  "Appointment Already exists! ";
    public AppointmentAlreadyExistsException(){
        super(DEFAULT_MESSAGE);
    }
}
