package com.example.demo.service.impl;

import com.example.demo.dto.OvertimeDto;
import com.example.demo.entity.Overtime;
import com.example.demo.entity.OvertimeSalary;
import com.example.demo.entity.User;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.mapper.OvertimeMapper;
import com.example.demo.payload.OvertimePayload;
import com.example.demo.dto.OvertimeUserDto;
import com.example.demo.repository.OvertimeRepository;
import com.example.demo.repository.OvertimeSalaryRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OvertimeService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OvertimeImpl implements OvertimeService {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class OvertimeData {
        private OvertimeSalary overtimeSalary;
        private User user;
    }

    private OvertimeData validateOvertimeData(OvertimePayload overtimePayload) {
        User user = userRepository.findById(overtimePayload.getUserId()).orElse(null);
        if (user == null) {
            throw new DoesNotExistException("User does not exist");
        }
        OvertimeSalary overtimeSalary = overtimeSalaryRepository.findById(overtimePayload.getOvertimeSalaryId()).orElse(null);
        if (overtimeSalary == null) {
            throw new DoesNotExistException("Overtime salary does not exist");
        }
        return new OvertimeData(overtimeSalary, user);
    }

    private final OvertimeRepository overtimeRepository;
    private final UserRepository userRepository;
    private final OvertimeSalaryRepository overtimeSalaryRepository;

    public OvertimeImpl(OvertimeRepository overtimeRepository, UserRepository userRepository, OvertimeSalaryRepository overtimeSalaryRepository) {
        this.overtimeRepository = overtimeRepository;
        this.userRepository = userRepository;
        this.overtimeSalaryRepository = overtimeSalaryRepository;
    }

    @Override
    public List<OvertimeDto> getOvertimes() {
        List<Overtime> overtimes = overtimeRepository.findAll();
        List<OvertimeDto> overtimeDtoList = new ArrayList<>();
        for (Overtime overtime : overtimes) {
            overtimeDtoList.add(OvertimeMapper.toOvertimeDto(overtime));
        }
        return overtimeDtoList;
    }

    @Override
    public OvertimeDto getOvertimeById(Long id) {
        Overtime overtime = overtimeRepository.findById(id).orElse(null);
        if (overtime == null) {
            throw new DoesNotExistException("Overtime does not exist");
        }
        return OvertimeMapper.toOvertimeDto(overtime);
    }

    @Override
    public OvertimeDto createOvertime(OvertimePayload overtimePayload) {
        OvertimeData overtimeData = validateOvertimeData(overtimePayload);
        Overtime newOvertime = new Overtime();
        OvertimeMapper.mapOvertime(newOvertime, overtimePayload, overtimeData.getUser(), overtimeData.getOvertimeSalary());
        overtimeRepository.save(newOvertime);
        return OvertimeMapper.toOvertimeDto(newOvertime);
    }

    @Override
    public OvertimeDto updateOvertime(OvertimePayload overtimePayload) {
        Overtime overtime = overtimeRepository.findById(overtimePayload.getId()).orElse(null);
        if (overtime == null) {
            throw new DoesNotExistException("Overtime does not exist");
        }
        OvertimeData overtimeData = validateOvertimeData(overtimePayload);
        OvertimeMapper.mapOvertime(overtime, overtimePayload, overtimeData.getUser(), overtimeData.getOvertimeSalary());
        overtimeRepository.save(overtime);
        return OvertimeMapper.toOvertimeDto(overtime);
    }

    @Override
    public OvertimeDto deleteOvertime(Long id) {
        Overtime overtime = overtimeRepository.findById(id).orElse(null);
        if (overtime == null) {
            throw new DoesNotExistException("Overtime does not exist");
        }
        if (overtime.getUser() != null) {
            User user = overtime.getUser();
            List<Overtime> overtimes = user.getOvertimes();
            overtimes.remove(overtime);
            userRepository.save(user);
            overtime.setUser(null);
        }
        if (overtime.getOvertimeSalary() != null) {
            overtime.setOvertimeSalary(null);
        }
        overtimeRepository.delete(overtime);
        return OvertimeMapper.toOvertimeDto(overtime);
    }

    @Override
    public List<OvertimeUserDto> getOvertimeByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new DoesNotExistException("User does not exist");
        }
        List<Overtime> overtimes = user.getOvertimes();
        List<OvertimeUserDto> overtimeUserDtoList = new ArrayList<>();
        for(Overtime overtime : overtimes) {
            overtimeUserDtoList.add(OvertimeMapper.toOvertimePayloadUser(overtime));
        }
        return overtimeUserDtoList;
    }
}
