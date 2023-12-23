package com.example.ressyappointmentservice.scheduler;

import com.example.ressyappointmentservice.entity.Appointment;
import com.example.ressyappointmentservice.repository.AppointmentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AppointmentScheduler {

    private final AppointmentRepository appointmentRepository;

    public AppointmentScheduler(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void checkAndUpdateAppointments() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        List<Appointment> appointments = appointmentRepository.findByUpcomingAndCancelledAndCompletedAndScheduleDateTimeBefore(
                1, 0, 0, currentDateTime);

        appointments.forEach(appointment -> {
            appointment.setUpcoming(0);
            appointment.setCompleted(1);
            appointmentRepository.save(appointment);
        });

        System.out.println("Scheduled task executed!");
    }
}
