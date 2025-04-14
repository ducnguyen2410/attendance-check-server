package com.example.demo.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressPayload {
    private Long id;
    private Long userDataId;
    private String houseNumber;
    private String street;
    private String city;
    private String district;
}
