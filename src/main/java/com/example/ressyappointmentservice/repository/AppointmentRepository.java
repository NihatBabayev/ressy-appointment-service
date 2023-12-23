package com.example.ressyappointmentservice.repository;

import com.example.ressyappointmentservice.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // By customer
    List<Appointment> findAllByUpcomingAndCustomerEmailOrderByScheduleDateTime(Integer upcoming, String customerEmail);
    List<Appointment> findAllByCancelledAndCustomerEmailOrderByScheduleDateTime(Integer cancelled, String customerEmail);

    List<Appointment> findAllByCompletedAndCustomerEmailOrderByScheduleDateTime(Integer completed, String customerEmail);

    // By doctor

    List<Appointment> findAllByUpcomingAndDoctorEmailOrderByScheduleDateTime(Integer upcoming, String doctorEmail);
    List<Appointment> findAllByCancelledAndDoctorEmailOrderByScheduleDateTime(Integer cancelled, String doctorEmail);
    List<Appointment> findAllByCompletedAndDoctorEmailOrderByScheduleDateTime(Integer completed, String doctorEmail);

    Appointment findByUpcomingAndScheduleDateTimeAndPatientNameAndDoctorNameAndCustomerEmailAndDoctorProfessionAndDoctorEmail(Integer upcoming, LocalDateTime scheduleDateTime, String patientName, String doctorName,String customerEmail, String doctorProfession, String doctorEmail);

    Appointment findByCancelledAndCustomerEmailAndDoctorNameAndScheduleDateTime(Integer canceled, String customerEmail, String doctorName,LocalDateTime scheduleDateTime );

    Appointment findByCompletedAndCustomerEmailAndDoctorNameAndScheduleDateTimeAndDoctorProfession(Integer completed, String customerEmail, String doctorName, LocalDateTime scheduleDateTime, String doctorProfession);

    List<Appointment> findByUpcomingAndCancelledAndCompletedAndScheduleDateTimeBefore(
            Integer upcoming, Integer cancelled, Integer completed, LocalDateTime scheduleDateTime);

}