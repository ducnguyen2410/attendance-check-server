package com.example.demo.service;

import com.example.demo.dto.ContractDto;
import com.example.demo.entity.Contract;
import com.example.demo.payload.ContractPayload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContractService {
    public abstract List<ContractDto> getAllContracts();
    public abstract ContractDto getContractById(Long id);
    public abstract ContractDto createContract(ContractPayload contract);
    public abstract ContractDto updateContract(ContractPayload contract);
    public abstract ContractDto deleteContract(Long id);

}
