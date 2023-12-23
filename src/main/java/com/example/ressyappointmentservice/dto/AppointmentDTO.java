package com.example.ressyappointmentservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {
    private String doctorName;
    private String doctorProfession;
    private String doctorEmail;
    private Integer upcoming;
    private Integer cancelled;
    private Integer completed;
    private String scheduleDate;
    private String scheduleTime;
    private String patientName;
    private String patientGender;
    private Integer patientAge;
    private String patientProblem;
    private String doctorPhoto;
    private String customerEmail;
}
