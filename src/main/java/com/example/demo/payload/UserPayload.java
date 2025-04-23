package com.example.demo.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserPayload {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Long positionId;
    private Long positionLevelId;
    private Long userDataId;
    private Long contractId;
    private List<AttendancePayload> attendancePayloads;
    private List<OvertimePayload> overtimePayloads;
    private List<String> roles;
}
