package com.example.demo.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OvertimeSalaryDto {
    private Long id;
    private String overtimeType;
    private Float overtimeCharge;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
