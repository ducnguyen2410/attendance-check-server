package com.example.demo.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OvertimePayload {
    private Long id;
    private Long userId;
    private Long overtimeSalaryId;
    private Float duration;
}
