package com.example.demo.repository;

import com.example.demo.entity.OvertimeSalary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OvertimeSalaryRepository extends JpaRepository<OvertimeSalary, Long> {
}
