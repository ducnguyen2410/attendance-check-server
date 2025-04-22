package com.example.demo.repository;

import com.example.demo.entity.ContractType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractTypeRepository extends JpaRepository<ContractType, Long> {
    public abstract ContractType findByContractType(String contractTypeName);
}
