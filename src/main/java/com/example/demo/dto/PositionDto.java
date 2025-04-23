package com.example.demo.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PositionDto {
    private Long id;
    private String positionName;
    private Float baseSalary;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
