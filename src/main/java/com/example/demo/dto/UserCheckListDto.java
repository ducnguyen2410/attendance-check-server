package com.example.demo.dto;

import com.example.demo.entity.Attendance;
import com.example.demo.entity.Overtime;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCheckListDto {
    private String username;
    private Long userId;
    private List<AttendanceDto> attendances;
    private List<OvertimeDto> overtimes;
}
