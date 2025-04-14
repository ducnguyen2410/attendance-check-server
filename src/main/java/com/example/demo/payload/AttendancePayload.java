package com.example.demo.payload;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AttendancePayload {
    private Long id;
    private Long userId;
    private String checkIn;
    private String checkOut;
}
