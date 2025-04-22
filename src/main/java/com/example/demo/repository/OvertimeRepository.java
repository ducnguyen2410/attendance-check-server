package com.example.demo.repository;

import com.example.demo.entity.Overtime;
import com.example.demo.entity.OvertimeSalary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OvertimeRepository extends JpaRepository<Overtime, Long> {
    public abstract List<Overtime> getOvertimeByOvertimeSalary(OvertimeSalary overtimeSalary);
}
