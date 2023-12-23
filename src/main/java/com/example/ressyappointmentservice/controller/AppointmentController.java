package com.example.ressyappointmentservice.controller;

import com.example.ressyappointmentservice.config.JwtService;
import com.example.ressyappointmentservice.dto.AppointmentDTO;
import com.example.ressyappointmentservice.dto.AppointmentRequestBody;
import com.example.ressyappointmentservice.dto.ResponseModel;
import com.example.ressyappointmentservice.exception.TokenIsExpiredException;
import com.example.ressyappointmentservice.service.AppointmentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("appointment")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final JwtService jwtService;

    @PostMapping("/schedule")
    public ResponseEntity<ResponseModel<String>> scheduleAppointment(HttpServletRequest httpServletRequest,
                                                                     @RequestBody AppointmentRequestBody appointmentRequestBody) {
        String authHeader = httpServletRequest.getHeader("Authorization");
        String userEmail = jwtService.extractUsernameFromHeader(authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if(jwtService.isTokenExpired(token)){
                throw new TokenIsExpiredException();
            }
        }
        return new ResponseEntity<>(appointmentService.scheduleAppointment(userEmail, appointmentRequestBody), HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<ResponseModel<List<AppointmentDTO>>> getUpcomingAppointments(HttpServletRequest httpServletRequest,
                                                                                       @RequestParam("type") String type,
                                                                                       @RequestParam("user") String userType){
        String authHeader = httpServletRequest.getHeader("Authorization");
        String userEmail = jwtService.extractUsernameFromHeader(authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if(jwtService.isTokenExpired(token)){
                throw new TokenIsExpiredException();
            }
        }
        return new ResponseEntity<>(appointmentService.getAppointments(userEmail, type, userType), HttpStatus.OK);
    }
    @PostMapping("/cancel")
    public ResponseEntity<ResponseModel<String>> cancelAppointment(HttpServletRequest httpServletRequest,
                                                                   @RequestBody AppointmentRequestBody appointmentRequestBody){
        String authHeader = httpServletRequest.getHeader("Authorization");
        String userEmail = jwtService.extractUsernameFromHeader(authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if(jwtService.isTokenExpired(token)){
                throw new TokenIsExpiredException();
            }
        }
        return new ResponseEntity<>(appointmentService.cancelAppointment(userEmail, appointmentRequestBody), HttpStatus.OK);
    }
    @PostMapping("/reschedule")
    public ResponseEntity<ResponseModel<String>> reScheduleAppointment(HttpServletRequest httpServletRequest,
                                                                       @RequestBody AppointmentRequestBody appointmentRequestBody,
                                                                       @RequestParam(value = "scheduleDate", required = false) String newDate,
                                                                       @RequestParam(value = "scheduleTime", required = false) String newTime,
                                                                       @RequestParam(value = "patientProblem", required = false) String patientProblem){
        String authHeader = httpServletRequest.getHeader("Authorization");
        String userEmail = jwtService.extractUsernameFromHeader(authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if(jwtService.isTokenExpired(token)){
                throw new TokenIsExpiredException();
            }
        }
        return new ResponseEntity<>(appointmentService.reScheduleAppointment(userEmail, appointmentRequestBody, newDate, newTime , patientProblem), HttpStatus.OK);

    }
    @PostMapping("/rebook")
    public ResponseEntity<ResponseModel<String>> reBookAppointment(HttpServletRequest httpServletRequest,
                                                                   @RequestBody AppointmentRequestBody appointmentRequestBody){
        String authHeader = httpServletRequest.getHeader("Authorization");
        String userEmail = jwtService.extractUsernameFromHeader(authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if(jwtService.isTokenExpired(token)){
                throw new TokenIsExpiredException();
            }
        }
        return new ResponseEntity<>(appointmentService.reBookAppointment(userEmail, appointmentRequestBody), HttpStatus.OK);
    }

}