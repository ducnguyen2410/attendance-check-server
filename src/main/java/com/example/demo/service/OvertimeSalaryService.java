package com.example.demo.service;

import com.example.demo.dto.OvertimeSalaryDto;
import com.example.demo.entity.OvertimeSalary;
import com.example.demo.payload.OvertimeSalaryPayload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OvertimeSalaryService {
    public abstract List<OvertimeSalaryDto> getAllOvertimeSalary();
    public abstract OvertimeSalaryDto getOvertimeSalaryById(Long id);
    public abstract OvertimeSalaryDto createOvertimeSalary(OvertimeSalaryPayload overtimeSalaryPayload);
    public abstract OvertimeSalaryDto updateOvertimeSalary(OvertimeSalaryPayload overtimeSalaryPayload);
    public abstract OvertimeSalaryDto deleteOvertimeSalary(Long id);
}
