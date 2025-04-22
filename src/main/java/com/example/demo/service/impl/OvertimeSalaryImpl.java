package com.example.demo.service.impl;

import com.example.demo.dto.OvertimeSalaryDto;
import com.example.demo.entity.Overtime;
import com.example.demo.entity.OvertimeSalary;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.mapper.OvertimeSalaryMapper;
import com.example.demo.payload.OvertimeSalaryPayload;
import com.example.demo.repository.OvertimeRepository;
import com.example.demo.repository.OvertimeSalaryRepository;
import com.example.demo.service.OvertimeSalaryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OvertimeSalaryImpl implements OvertimeSalaryService {

    private final OvertimeSalaryRepository overtimeSalaryRepository;
    private final OvertimeRepository overtimeRepository;

    public OvertimeSalaryImpl(OvertimeSalaryRepository overtimeSalaryRepository, OvertimeRepository overtimeRepository) {
        this.overtimeSalaryRepository = overtimeSalaryRepository;
        this.overtimeRepository = overtimeRepository;
    }

    @Override
    public List<OvertimeSalaryDto> getAllOvertimeSalary() {
        List<OvertimeSalary> overtimeSalaries = overtimeSalaryRepository.findAll();
        List<OvertimeSalaryDto> overtimeSalaryDtoList = new ArrayList<>();
        for (OvertimeSalary overtimeSalary : overtimeSalaries) {
            overtimeSalaryDtoList.add(OvertimeSalaryMapper.toOvertimeSalaryDto(overtimeSalary));
        }
        return overtimeSalaryDtoList;
    }

    @Override
    public OvertimeSalaryDto getOvertimeSalaryById(Long id) {
        OvertimeSalary overtimeSalary = overtimeSalaryRepository.findById(id).orElse(null);
        if(overtimeSalary == null) return null;
        return OvertimeSalaryMapper.toOvertimeSalaryDto(overtimeSalary);
    }

    @Override
    public OvertimeSalaryDto createOvertimeSalary(OvertimeSalaryPayload overtimeSalaryPayload) {
        OvertimeSalary overtimeSalary = OvertimeSalaryMapper.toOvertimeSalary(overtimeSalaryPayload);
        overtimeSalaryRepository.save(overtimeSalary);
        return OvertimeSalaryMapper.toOvertimeSalaryDto(overtimeSalary);
    }

    @Override
    public OvertimeSalaryDto updateOvertimeSalary(OvertimeSalaryPayload overtimeSalaryPayload) {
        OvertimeSalary overtimeSalary = overtimeSalaryRepository.findById(overtimeSalaryPayload.getId())
                .orElseThrow(() -> new DoesNotExistException("Overtime Salary does not exist"));
        OvertimeSalaryMapper.mapOvertimeSalary(overtimeSalary, overtimeSalaryPayload);
        overtimeSalaryRepository.save(overtimeSalary);
        return OvertimeSalaryMapper.toOvertimeSalaryDto(overtimeSalary);
    }

    @Override
    public OvertimeSalaryDto deleteOvertimeSalary(Long id) {
        try {
            OvertimeSalary overtimeSalary = overtimeSalaryRepository.findById(id)
                    .orElseThrow(() -> new DoesNotExistException("Overtime Salary does not exist"));
            List<Overtime> overtimes = overtimeRepository.getOvertimeByOvertimeSalary(overtimeSalary);
            for(Overtime overtime : overtimes) {
                overtime.setOvertimeSalary(null);
            }
            overtimeRepository.saveAll(overtimes);
            overtimeSalaryRepository.deleteById(id);
            return OvertimeSalaryMapper.toOvertimeSalaryDto(overtimeSalary);
        } catch (RuntimeException e) {
            throw new DoesNotExistException("Overtime Salary not found");
        }
    }
}
