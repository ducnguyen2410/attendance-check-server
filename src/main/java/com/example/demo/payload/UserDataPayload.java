package com.example.demo.payload;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDataPayload {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String phone;
    private AddressPayload address;
}
