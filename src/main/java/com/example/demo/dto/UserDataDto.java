package com.example.demo.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDataDto {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate birthDate;
    private AddressDto address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
