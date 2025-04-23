package com.example.demo.repository;

import com.example.demo.entity.Contract;
import com.example.demo.entity.ContractType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    public List<Contract> findByContractType(ContractType contractType);
}
