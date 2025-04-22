package com.example.demo.mapper;

import com.example.demo.dto.OvertimeDto;
import com.example.demo.entity.Overtime;
import com.example.demo.entity.OvertimeSalary;
import com.example.demo.entity.User;
import com.example.demo.payload.OvertimePayload;
import com.example.demo.dto.OvertimeUserDto;

public class OvertimeMapper {
    public static Overtime toOvertime(OvertimePayload overtimePayload, User user, OvertimeSalary overtimeSalary) {
        return Overtime.builder()
                .user(user)
                .overtimeSalary(overtimeSalary)
                .duration(overtimePayload.getDuration())
                .build();
    }

    public static void mapOvertime(Overtime overtime, OvertimePayload overtimePayload, User user, OvertimeSalary overtimeSalary) {
        if(overtimePayload.getDuration() != null) overtime.setDuration(overtimePayload.getDuration());
        overtime.setUser(user);
        overtime.setOvertimeSalary(overtimeSalary);
    }

    public static OvertimeDto toOvertimeDto(Overtime overtime) {
        return OvertimeDto.builder()
                .id(overtime.getId())
                .userId(overtime.getUser().getId())
                .overtimeSalaryId(overtime.getOvertimeSalary() != null ? overtime.getOvertimeSalary().getId() : null)
                .duration(overtime.getDuration())
                .createdTime(overtime.getCreatedTime())
                .updatedTime(overtime.getUpdatedTime())
                .build();
    }

    public static OvertimeUserDto toOvertimePayloadUser(Overtime overtime) {
        return OvertimeUserDto.builder()
                .id(overtime.getId())
                .createTime(overtime.getCreatedTime())
                .updateTime(overtime.getCreatedTime())
                .duration(overtime.getDuration())
                .build();
    }
}
