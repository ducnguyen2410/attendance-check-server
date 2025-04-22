package com.example.demo.mapper;

import com.example.demo.dto.AttendanceDto;
import com.example.demo.entity.Attendance;
import com.example.demo.entity.User;
import com.example.demo.payload.AttendancePayload;
import com.example.demo.util.DateUtil;

public class AttendanceMapper {
    public static Attendance toAttendance(AttendancePayload attendancePayload, User user) {
        return Attendance.builder()
                .checkOut(DateUtil.toLocalTime(attendancePayload.getCheckOut()))
                .checkIn(DateUtil.toLocalTime(attendancePayload.getCheckIn()))
                .user(user)
                .build();
    }

    public static void mapAttendance(Attendance attendance, AttendancePayload attendancePayload, User user) {
        if(attendancePayload.getCheckIn() != null) attendance.setCheckIn(DateUtil.toLocalTime(attendancePayload.getCheckIn()));
        if(attendancePayload.getCheckOut() != null) attendance.setCheckOut(DateUtil.toLocalTime(attendancePayload.getCheckOut()));
        attendance.setUser(user);
    }

    public static AttendanceDto toAttendanceDto(Attendance attendance) {
        return AttendanceDto.builder()
                .id(attendance.getId())
                .userId(attendance.getUser().getId())
                .createdTime(attendance.getCreatedTime())
                .updatedTime(attendance.getUpdatedTime())
                .checkIn(attendance.getCheckIn())
                .checkOut(attendance.getCheckOut())
                .build();
    }
}
