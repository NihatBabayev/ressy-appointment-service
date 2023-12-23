package com.example.ressyappointmentservice.dto;

import lombok.Data;

@Data
public class ResponseModel<T> {
    T data;
    String message;
}