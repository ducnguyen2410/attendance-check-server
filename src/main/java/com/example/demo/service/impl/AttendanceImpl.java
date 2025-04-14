package com.example.demo.service.impl;

import com.example.demo.dto.AttendanceDto;
import com.example.demo.entity.Attendance;
import com.example.demo.entity.User;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.mapper.AttendanceMapper;
import com.example.demo.payload.AttendancePayload;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AttendanceService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceImpl implements AttendanceService {

    @Getter
    @Setter
    private static class ValidatedAttendanceData{
        private final User user;

        private ValidatedAttendanceData(User user) {
            this.user = user;
        }
    }

    private ValidatedAttendanceData validatedAttendanceData(AttendancePayload attendancePayload){
        User user = userRepository.findById(attendancePayload.getUserId()).orElse(null);
        if (user == null){
           throw new DoesNotExistException("User not found");
        }
        return new ValidatedAttendanceData(user);
    }

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    public AttendanceImpl(AttendanceRepository attendanceRepository, UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<AttendanceDto> getAllAttendance() {
        List<Attendance> attendanceList = attendanceRepository.findAll();
        List<AttendanceDto> attendanceDtoList = new ArrayList<>();
        for (Attendance attendance : attendanceList) {
            attendanceDtoList.add(AttendanceMapper.toAttendanceDto(attendance));
        }
        return attendanceDtoList;
    }

    @Override
    public AttendanceDto getAttendanceById(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new DoesNotExistException("Attendance not found"));
        return AttendanceMapper.toAttendanceDto(attendance);
    }

    @Override
    public AttendanceDto createAttendance(AttendancePayload attendancePayload) {
        ValidatedAttendanceData attendanceData = validatedAttendanceData(attendancePayload);
        User user = attendanceData.getUser();
        Attendance attendance = AttendanceMapper.toAttendance(attendancePayload, user);
        List<Attendance> attendanceList = user.getAttendances();
        attendanceList.add(attendance);
        user.setAttendances(attendanceList);
        attendanceRepository.save(attendance);
        return AttendanceMapper.toAttendanceDto(attendance);
    }

    @Override
    public AttendanceDto updateAttendance(AttendancePayload attendancePayload) {
        ValidatedAttendanceData attendanceData = validatedAttendanceData(attendancePayload);
        Attendance attendance = attendanceRepository.findById(attendancePayload.getId()).orElse(null);
        if (attendance == null) {
            throw new DoesNotExistException("Attendance does not exist");
        }
        User user = attendanceData.getUser();
        AttendanceMapper.mapAttendance(attendance, attendancePayload, user);
        List<Attendance> attendanceList = user.getAttendances();
        if (attendanceList == null) {
            attendanceList = new ArrayList<>();
        }
        attendanceList.add(attendance);
        user.setAttendances(attendanceList);
        attendanceRepository.save(attendance);
        return AttendanceMapper.toAttendanceDto(attendance);
    }

    @Override
    public AttendanceDto deleteAttendance(Long id) {
        try {
            Attendance attendance = attendanceRepository.findById(id).orElse(null);
            if (attendance == null) {
                throw new DoesNotExistException("Tardy does not exist");
            }
            User user = attendance.getUser();
            List<Attendance> attendanceList = user.getAttendances();
            attendanceList.remove(attendance);
            user.setAttendances(attendanceList);
            attendanceRepository.delete(attendance);
            return AttendanceMapper.toAttendanceDto(attendance);
        } catch (RuntimeException e) {
            throw new DoesNotExistException("Tardy does not exist");
        }
    }
}
