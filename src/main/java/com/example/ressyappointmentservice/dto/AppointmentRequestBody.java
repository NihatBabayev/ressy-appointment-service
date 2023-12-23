package com.example.ressyappointmentservice.dto;

import lombok.Data;

@Data
public class AppointmentRequestBody {

    String doctorName;
    String doctorProfession;
    String doctorEmail;
    String patientName;
    Integer patientAge;
    String patientGender;
    String patientProblem;
    String appointmentDate;
    String appointmentTime;
    String doctorPhoto;

}