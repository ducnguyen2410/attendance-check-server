package com.example.demo.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private Long id;
    private Long userDataId;
    private String houseNumber;
    private String street;
    private String city;
    private String district;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
