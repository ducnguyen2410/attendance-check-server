package com.example.demo.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OvertimeDto {
    private Long id;
    private Long userId;
    private Long overtimeSalaryId;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private Float duration;
}
