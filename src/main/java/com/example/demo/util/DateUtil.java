package com.example.demo.util;

import com.example.demo.exception.WrongDateTimeFormatException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    public static LocalDate toLocalDate(String date) {
        if (date == null || date.isBlank()) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, formatter);
        } catch (Exception e) {
            throw new WrongDateTimeFormatException("Error in converting LocalDate");
        }
    }

    public static LocalTime toLocalTime(String time) {
        if (time == null || time.isBlank()) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(time, formatter);
        } catch (Exception e) {
            throw new WrongDateTimeFormatException("Error in converting LocalTime");
        }
    }
}
