package com.example.demo.dto;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractDto {
    private Long id;
    private String contractName;
    private ContractTypeDto contractType;
    private Long userId;
    private LocalTime startHour;
    private LocalTime endHour;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
