package com.example.demo.payload;

import com.example.demo.entity.ContractType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class ContractPayload {
    private Long id;
    private String contractName;
    private Long userId;
    private Long contractTypeId;
    private String startHour;
    private String endHour;
    private String startDate;
    private String endDate;
}
