package com.example.demo.service;

import com.example.demo.dto.OvertimeDto;
import com.example.demo.entity.Overtime;
import com.example.demo.payload.OvertimePayload;
import com.example.demo.dto.OvertimeUserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OvertimeService {
    public abstract List<OvertimeDto> getOvertimes();
    public abstract OvertimeDto getOvertimeById(Long id);
    public abstract OvertimeDto createOvertime(OvertimePayload overtime);
    public abstract OvertimeDto updateOvertime(OvertimePayload overtime);
    public abstract OvertimeDto deleteOvertime(Long id);
    public abstract List<OvertimeUserDto> getOvertimeByUserId(Long userId);
}
