package com.example.demo.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OvertimeSalaryPayload {
    private Long id;
    private String overtimeType;
    private Float overtimeCharge;
}
