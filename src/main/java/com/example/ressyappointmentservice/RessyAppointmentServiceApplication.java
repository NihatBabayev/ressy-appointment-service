package com.example.ressyappointmentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RessyAppointmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RessyAppointmentServiceApplication.class, args);
    }

}
