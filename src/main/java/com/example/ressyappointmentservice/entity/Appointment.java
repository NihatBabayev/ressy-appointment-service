package com.example.ressyappointmentservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments", schema = "ressy_appointment")
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String doctorEmail;
    private String doctorProfession;
    private String customerEmail;
    private String doctorName;
    private Integer upcoming;
    private Integer cancelled;
    private Integer completed;
    private LocalDateTime scheduleDateTime;
    private String patientName;
    private String patientGender;
    private Integer patientAge;
    private String patientProblem;

    @Column(columnDefinition = "TEXT")
    private String doctorPhoto;


}