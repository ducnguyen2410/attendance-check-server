package com.example.demo.service.impl;

import com.example.demo.dto.ContractTypeDto;
import com.example.demo.entity.Contract;
import com.example.demo.entity.ContractType;
import com.example.demo.exception.AlreadyExistsException;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.mapper.ContractTypeMapper;
import com.example.demo.payload.ContractTypePayload;
import com.example.demo.repository.ContractRepository;
import com.example.demo.repository.ContractTypeRepository;
import com.example.demo.service.ContractTypeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContractTypeImpl implements ContractTypeService {

    private final ContractTypeRepository contractTypeRepository;
    private final ContractRepository contractRepository;

    public ContractTypeImpl(ContractTypeRepository contractTypeRepository, ContractRepository contractRepository) {
        this.contractTypeRepository = contractTypeRepository;
        this.contractRepository = contractRepository;
    }

    @Override
    public List<ContractTypeDto> getContractTypes() {
        List<ContractType> contractTypes = contractTypeRepository.findAll();
        List<ContractTypeDto> contractTypeDtoList = new ArrayList<>();
        for (ContractType contractType : contractTypes) {
            contractTypeDtoList.add(ContractTypeMapper.toContractTypeDto(contractType));
        }
        return contractTypeDtoList;
    }

    @Override
    public ContractTypeDto getContractTypeById(Long id) {
        ContractType contractType = contractTypeRepository.findById(id).orElse(null);
        if (contractType == null) {
            throw new DoesNotExistException("Contract type does not exist");
        }
        return ContractTypeMapper.toContractTypeDto(contractType);
    }

    @Override
    public ContractTypeDto createContractType(ContractTypePayload contractTypePayload) {
        ContractType contractType = contractTypeRepository.findByContractType(contractTypePayload.getContractType());
        if (contractType != null) {
            throw new AlreadyExistsException("Contract type already exists");
        }
        ContractType newContractType = ContractTypeMapper.toContractType(contractTypePayload);
        contractTypeRepository.save(newContractType);
        return ContractTypeMapper.toContractTypeDto(newContractType);
    }

    @Override
    public ContractTypeDto updateContractType(ContractTypePayload contractTypePayload) {
        ContractType contractType = contractTypeRepository.findById(contractTypePayload.getId()).orElse(null);
        if (contractType == null) {
            throw new DoesNotExistException("Contract type does not exist");
        }
        ContractTypeMapper.mapContractType(contractType, contractTypePayload);
        contractTypeRepository.save(contractType);
        return ContractTypeMapper.toContractTypeDto(contractType);
    }

    @Override
    public ContractTypeDto deleteContractType(Long id) {
        try {
            ContractType contractType = contractTypeRepository.findById(id).orElse(null);
            if (contractType == null) {
                throw new DoesNotExistException("Contract type does not exist");
            }
            List<Contract> contracts = contractRepository.findByContractType(contractType);
            for (Contract contract : contracts) {
                contract.setContractType(null);
            }
            contractRepository.saveAll(contracts);
            contractTypeRepository.deleteById(id);
            return ContractTypeMapper.toContractTypeDto(contractType);
        } catch (RuntimeException e) {
            throw new DoesNotExistException("Contract type does not exist");
        }
    }
}
