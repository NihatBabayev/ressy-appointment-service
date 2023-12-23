package com.example.ressyappointmentservice.service.Impl;

import com.example.ressyappointmentservice.dto.AppointmentDTO;
import com.example.ressyappointmentservice.dto.AppointmentRequestBody;
import com.example.ressyappointmentservice.dto.ResponseModel;
import com.example.ressyappointmentservice.entity.Appointment;
import com.example.ressyappointmentservice.exception.AppointmentAlreadyExistsException;
import com.example.ressyappointmentservice.exception.AppointmentNotFoundException;
import com.example.ressyappointmentservice.repository.AppointmentRepository;
import com.example.ressyappointmentservice.service.AppointmentService;
import com.example.ressyappointmentservice.util.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Override
    public ResponseModel<String> scheduleAppointment(String customerEmail, AppointmentRequestBody appointmentRequestBody) {
        Appointment appointment = new Appointment();
        LocalDateTime scheduleDateTime = Utils.StringToLocalDateTime(appointmentRequestBody.getAppointmentDate() + " " +appointmentRequestBody.getAppointmentTime());
        Appointment foundedAppointment = appointmentRepository.findByUpcomingAndScheduleDateTimeAndPatientNameAndDoctorNameAndCustomerEmailAndDoctorProfessionAndDoctorEmail
                (1, scheduleDateTime,
                        appointmentRequestBody.getPatientName(),
                        appointmentRequestBody.getDoctorName(),
                        customerEmail,
                        appointmentRequestBody.getDoctorProfession(),
                        appointmentRequestBody.getDoctorEmail());
        if (foundedAppointment != null) {
            throw new AppointmentAlreadyExistsException();
        }
        appointment.setCompleted(0);
        appointment.setCancelled(0);
        appointment.setUpcoming(1);
        appointment.setCustomerEmail(customerEmail);
        appointment.setDoctorName(appointmentRequestBody.getDoctorName());
        appointment.setDoctorEmail(appointmentRequestBody.getDoctorEmail());
        appointment.setDoctorProfession(appointmentRequestBody.getDoctorProfession());
        appointment.setScheduleDateTime(scheduleDateTime);
        appointment.setPatientAge(appointmentRequestBody.getPatientAge());
        appointment.setPatientName(appointmentRequestBody.getPatientName());
        appointment.setPatientGender(appointmentRequestBody.getPatientGender());
        appointment.setPatientProblem(appointmentRequestBody.getPatientProblem());
        appointment.setDoctorPhoto(appointmentRequestBody.getDoctorPhoto());

        appointmentRepository.save(appointment);
        ResponseModel<String> responseModel = new ResponseModel<>();
        responseModel.setMessage("Appointment Scheduled Successfully");
        return responseModel;
    }

    @Override
    public ResponseModel<List<AppointmentDTO>> getAppointments(String userEmail, String type, String userType) {
        List<Appointment> appointmentList = new ArrayList<>();
        if ("Upcoming".equals(type)) {
            if("customer".equals(userType)){
                appointmentList = appointmentRepository.findAllByUpcomingAndCustomerEmailOrderByScheduleDateTime(1, userEmail);
            } else if ("doctor".equals(userType)) {
                appointmentList = appointmentRepository.findAllByUpcomingAndDoctorEmailOrderByScheduleDateTime(1, userEmail);
            }
        } else if ("Cancelled".equals(type)) {
            if("customer".equals(userType)){
                appointmentList = appointmentRepository.findAllByCancelledAndCustomerEmailOrderByScheduleDateTime(1, userEmail);
            } else if ("doctor".equals(userType)) {
                appointmentList = appointmentRepository.findAllByCancelledAndDoctorEmailOrderByScheduleDateTime(1, userEmail);
            }
        } else if ("Completed".equals(type)) {
            if("customer".equals(userType)){
                appointmentList = appointmentRepository.findAllByCompletedAndCustomerEmailOrderByScheduleDateTime(1, userEmail);
            } else if ("doctor".equals(userType)) {
                appointmentList = appointmentRepository.findAllByCompletedAndDoctorEmailOrderByScheduleDateTime(1, userEmail);
            }
        }
        List<AppointmentDTO> appointmentDTOList = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            AppointmentDTO appointmentDTO = new AppointmentDTO();
            appointmentDTO.setPatientAge(appointment.getPatientAge());
            appointmentDTO.setDoctorProfession(appointment.getDoctorProfession());
            appointmentDTO.setDoctorName(appointment.getDoctorName());
            appointmentDTO.setDoctorEmail(appointment.getDoctorEmail());
            appointmentDTO.setCustomerEmail(appointment.getCustomerEmail());
            appointmentDTO.setPatientGender(appointment.getPatientGender());
            appointmentDTO.setPatientName(appointment.getPatientName());
            String dateTime = Utils.LocalDateTimeToString(appointment.getScheduleDateTime());
            String[] words = dateTime.split(" ");
            String date = words[0];
            String time = words[1];
            appointmentDTO.setScheduleDate(date);
            appointmentDTO.setScheduleTime(time);
            appointmentDTO.setPatientProblem(appointment.getPatientProblem());
            appointmentDTO.setDoctorPhoto(appointment.getDoctorPhoto());


            appointmentDTOList.add(appointmentDTO);
        }
        ResponseModel<List<AppointmentDTO>> responseModel = new ResponseModel<>();
        responseModel.setData(appointmentDTOList);
        responseModel.setMessage("Successfully Retrieved Upcoming Appointments");
        return responseModel;
    }
    @Override
    @Transactional
    public ResponseModel<String> cancelAppointment(String userEmail, AppointmentRequestBody appointmentRequestBody) {
        LocalDateTime scheduleDateTime = Utils.StringToLocalDateTime(appointmentRequestBody.getAppointmentDate() + " " + appointmentRequestBody.getAppointmentTime());

        Appointment appointment = appointmentRepository
                .findByUpcomingAndScheduleDateTimeAndPatientNameAndDoctorNameAndCustomerEmailAndDoctorProfessionAndDoctorEmail
                        (1, scheduleDateTime,
                                appointmentRequestBody.getPatientName(),
                                appointmentRequestBody.getDoctorName(),
                                userEmail,
                                appointmentRequestBody.getDoctorProfession(),
                                appointmentRequestBody.getDoctorEmail()
                        );


        if (appointment == null) {
            throw new AppointmentNotFoundException();
        }

        appointment.setUpcoming(0);
        appointment.setCancelled(1);
        appointmentRepository.save(appointment);
        ResponseModel<String> responseModel = new ResponseModel<>();
        responseModel.setMessage("Appointment canceled successfully");
        return responseModel;
    }
    @Override
    @Transactional
    public ResponseModel<String> reScheduleAppointment(String userEmail, AppointmentRequestBody appointmentRequestBody, String newDate, String newTime, String patientProblem) {
        Appointment appointment = appointmentRepository.findByUpcomingAndScheduleDateTimeAndPatientNameAndDoctorNameAndCustomerEmailAndDoctorProfessionAndDoctorEmail(1, Utils.StringToLocalDateTime(appointmentRequestBody.getAppointmentDate() + " " + appointmentRequestBody.getAppointmentTime()) , appointmentRequestBody.getPatientName(), appointmentRequestBody.getDoctorName(), userEmail, appointmentRequestBody.getDoctorProfession(), appointmentRequestBody.getDoctorEmail());
        if (appointment == null) {
            throw new AppointmentNotFoundException();
        }
        if (patientProblem != null) {
            appointment.setPatientProblem(patientProblem);
        }
        if (newDate != null && newTime != null) {
            appointment.setScheduleDateTime(Utils.StringToLocalDateTime(newDate+" "+newTime));
        }
        appointmentRepository.save(appointment);
        ResponseModel<String> responseModel = new ResponseModel<>();
        responseModel.setMessage("Appointment rescheduled successfully");
        return responseModel;
    }
    @Override
    @Transactional
    public ResponseModel<String> reBookAppointment(String userEmail, AppointmentRequestBody appointmentRequestBody) {
        Appointment appointment = appointmentRepository.findByCompletedAndCustomerEmailAndDoctorNameAndScheduleDateTimeAndDoctorProfession(1, userEmail, appointmentRequestBody.getDoctorName(), Utils.StringToLocalDateTime(appointmentRequestBody.getAppointmentDate()+" "+appointmentRequestBody.getAppointmentTime()), appointmentRequestBody.getDoctorProfession());
        if (appointment == null) {
            throw new AppointmentNotFoundException();
        }
        appointment.setCompleted(0);
        appointment.setUpcoming(1);
        appointment.setScheduleDateTime(Utils.StringToLocalDateTime(appointmentRequestBody.getAppointmentDate() + " " + appointmentRequestBody.getAppointmentTime()));
        appointmentRepository.save(appointment);
        ResponseModel<String> responseModel = new ResponseModel<>();
        return responseModel;
    }

}
