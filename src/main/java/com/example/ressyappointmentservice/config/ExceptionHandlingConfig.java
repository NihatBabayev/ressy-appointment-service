package com.example.ressyappointmentservice.config;

import com.example.ressyappointmentservice.dto.ResponseModel;
import com.example.ressyappointmentservice.exception.AppointmentAlreadyExistsException;
import com.example.ressyappointmentservice.exception.AppointmentNotFoundException;
import com.example.ressyappointmentservice.exception.TokenIsExpiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class ExceptionHandlingConfig {

    @ExceptionHandler
            ({
                    AppointmentNotFoundException.class,
                    AppointmentAlreadyExistsException.class,
                    TokenIsExpiredException.class
            })
    public ResponseEntity<ResponseModel<String>> handleCustomExceptions(Exception ex) throws Exception {
        ResponseModel<String> exceptionResponseModel = new ResponseModel<>();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        StringBuilder errorMessage = new StringBuilder(ex.getMessage());


        if (ex instanceof AppointmentAlreadyExistsException || ex instanceof AppointmentNotFoundException || ex instanceof TokenIsExpiredException) {
            httpStatus = HttpStatus.BAD_REQUEST;
        }


        exceptionResponseModel.setMessage(String.valueOf(errorMessage));
        return new ResponseEntity<>(exceptionResponseModel, httpStatus);
    }


}