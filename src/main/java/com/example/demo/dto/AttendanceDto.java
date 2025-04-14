package com.example.demo.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceDto {
    private Long id;
    private Long userId;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalTime checkIn;
    private LocalTime checkOut;
}
