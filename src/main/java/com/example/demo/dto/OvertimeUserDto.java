package com.example.demo.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OvertimeUserDto {
    private Long id;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Float duration;
}
