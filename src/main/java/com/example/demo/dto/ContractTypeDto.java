package com.example.demo.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractTypeDto {
    private Long id;
    private String contractType;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
