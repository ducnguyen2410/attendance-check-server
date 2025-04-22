package com.example.demo.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionPayload {
    private Long id;
    private String positionName;
    private Float baseSalary;
}
