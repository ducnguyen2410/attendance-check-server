package com.example.demo.mapper;

import com.example.demo.dto.OvertimeDto;
import com.example.demo.dto.OvertimeSalaryDto;
import com.example.demo.entity.OvertimeSalary;
import com.example.demo.payload.OvertimeSalaryPayload;

public class OvertimeSalaryMapper {
    public static OvertimeSalary toOvertimeSalary(OvertimeSalaryPayload overtimeSalaryPayload) {
        return OvertimeSalary.builder()
                .overtimeType(overtimeSalaryPayload.getOvertimeType())
                .overtimeCharge(overtimeSalaryPayload.getOvertimeCharge())
                .build();
    }

    public static void mapOvertimeSalary(OvertimeSalary overtimeSalary, OvertimeSalaryPayload overtimeSalaryPayload) {
        if(overtimeSalaryPayload.getOvertimeCharge() != null) overtimeSalary.setOvertimeCharge(overtimeSalaryPayload.getOvertimeCharge());
        if(overtimeSalaryPayload.getOvertimeType() != null) overtimeSalary.setOvertimeType(overtimeSalaryPayload.getOvertimeType());
    }

    public static OvertimeSalaryDto toOvertimeSalaryDto(OvertimeSalary overtimeSalary) {
        return OvertimeSalaryDto.builder()
                .id(overtimeSalary.getId())
                .overtimeType(overtimeSalary.getOvertimeType())
                .overtimeCharge(overtimeSalary.getOvertimeCharge())
                .createdTime(overtimeSalary.getCreatedTime())
                .updatedTime(overtimeSalary.getUpdatedTime())
                .build();
    }
}
