package com.example.demo.service;

import com.example.demo.dto.ContractTypeDto;
import com.example.demo.entity.ContractType;
import com.example.demo.payload.ContractPayload;
import com.example.demo.payload.ContractTypePayload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContractTypeService {
    public abstract List<ContractTypeDto> getContractTypes();
    public abstract ContractTypeDto getContractTypeById(Long id);
    public abstract ContractTypeDto createContractType(ContractTypePayload contract);
    public abstract ContractTypeDto updateContractType(ContractTypePayload contract);
    public abstract ContractTypeDto deleteContractType(Long id);
}
