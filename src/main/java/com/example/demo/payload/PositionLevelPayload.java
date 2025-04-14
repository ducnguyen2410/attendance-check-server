package com.example.demo.payload;

import lombok.*;

@Getter
@Setter
public class PositionLevelPayload {
    private Long id;
    private String positionLevelName;
    private Float baseSalary;
}
