package com.example.ressyappointmentservice.service;

import com.example.ressyappointmentservice.dto.AppointmentDTO;
import com.example.ressyappointmentservice.dto.AppointmentRequestBody;
import com.example.ressyappointmentservice.dto.ResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppointmentService {
    ResponseModel<String> scheduleAppointment(String customerEmail, AppointmentRequestBody appointmentRequestBody);

//    ResponseModel<List<AppointmentDTO>> getUpcomingAppointments(String userEmail);

    ResponseModel<List<AppointmentDTO>> getAppointments(String userEmail, String type, String userType);

    ResponseModel<String> cancelAppointment(String userEmail, AppointmentRequestBody appointmentRequestBody);

    ResponseModel<String> reScheduleAppointment(String userEmail, AppointmentRequestBody appointmentRequestBody, String newDate, String newTime,  String patientProblem);

    ResponseModel<String> reBookAppointment(String userEmail, AppointmentRequestBody appointmentRequestBody);
}
