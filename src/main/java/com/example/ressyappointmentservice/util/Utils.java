package com.example.ressyappointmentservice.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static LocalDateTime StringToLocalDateTime(String localDateTimeString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTimeResult = LocalDateTime.parse(localDateTimeString, formatter);
        return localDateTimeResult;
    }
    public static String LocalDateTimeToString(LocalDateTime localDateTimeInput){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTimeInput.format(formatter);
        return formattedDateTime;
    }
}