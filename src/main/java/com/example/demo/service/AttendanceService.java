package com.example.demo.service;

import com.example.demo.dto.AttendanceDto;
import com.example.demo.payload.AttendancePayload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AttendanceService {
    public abstract List<AttendanceDto> getAllAttendance();
    public abstract AttendanceDto getAttendanceById(Long Id);
    public abstract AttendanceDto createAttendance(AttendancePayload attendancePayload);
    public abstract AttendanceDto updateAttendance(AttendancePayload attendancePayload);
    public abstract AttendanceDto deleteAttendance(Long attendanceId);
}
