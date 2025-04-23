package com.example.demo.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailDto {
    private Long id;
    private String username;
    private String email;
    private String position;
    private String positionLevel;
    private UserDataDto userData;
}
