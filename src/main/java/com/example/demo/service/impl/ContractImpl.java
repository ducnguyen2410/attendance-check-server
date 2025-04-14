package com.example.demo.service.impl;

import com.example.demo.dto.ContractDto;
import com.example.demo.dto.ContractTypeDto;
import com.example.demo.entity.Contract;
import com.example.demo.entity.ContractType;
import com.example.demo.entity.User;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.mapper.ContractMapper;
import com.example.demo.payload.ContractPayload;
import com.example.demo.repository.ContractRepository;
import com.example.demo.repository.ContractTypeRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ContractService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContractImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final ContractTypeRepository contractTypeRepository;
    private final UserRepository userRepository;

    public ContractImpl(ContractRepository contractRepository, ContractTypeRepository contractTypeRepository, UserRepository userRepository) {
        this.contractRepository = contractRepository;
        this.contractTypeRepository = contractTypeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ContractDto> getAllContracts() {
        List<Contract> contracts = contractRepository.findAll();
        List<ContractDto> contractDtoList = new ArrayList<>();
        for (Contract contract : contracts) {
            contractDtoList.add(ContractMapper.toContractDto(contract, contract.getUser()));
        }
        return contractDtoList;
    }

    @Override
    public ContractDto getContractById(Long id) {
        Contract contract = contractRepository.findById(id).orElse(null);
        if (contract == null) {
            throw new DoesNotExistException("Contract not found");
        }
        return ContractMapper.toContractDto(contract, contract.getUser());
    }

    @Override
    public ContractDto createContract(ContractPayload contractPayload) {
        User user = userRepository.findById(contractPayload.getUserId())
                .orElseThrow(() -> new DoesNotExistException("Invalid user"));
        ContractType contractType = contractTypeRepository.findById(contractPayload.getContractTypeId())
                .orElseThrow(() -> new DoesNotExistException("Invalid contract type"));
        Contract contract = ContractMapper.toContract(contractPayload, contractType);
        contract.setContractType(contractType);
        user.setContract(contract);
        contractRepository.save(contract);
        return ContractMapper.toContractDto(contract, user);
    }

    @Override
    public ContractDto updateContract(ContractPayload contractPayload) {
        Contract contract = contractRepository.findById(contractPayload.getId()).orElse(null);
        if (contract == null) {
            throw new DoesNotExistException("Contract does not exist");
        }
        User user = userRepository.findById(contractPayload.getUserId()).orElse(null);
        if (user == null) {
            throw new DoesNotExistException("Invalid user");
        }
        ContractType contractType = contractTypeRepository.findById(contractPayload.getContractTypeId()).orElse(null);
        if (contractType == null) {
            throw new DoesNotExistException("Invalid contract type");
        }
        ContractMapper.mapContract(contract, contractPayload, contractType);
        contract.setUser(user);
        user.setContract(contract);
        contractRepository.save(contract);
        return ContractMapper.toContractDto(contract, user);
    }

    @Override
    public ContractDto deleteContract(Long id) {
        try {
            Contract contract = contractRepository.findById(id).orElse(null);
            List<User> users = userRepository.findUserByContract(contract);
            if (contract == null) {
                throw new DoesNotExistException("Contract does not exist");
            }
            for (User user : users) {
                user.setContract(null);
            }
            userRepository.saveAll(users);
            contractRepository.deleteById(id);
            return ContractMapper.toContractDto(contract, contract.getUser());
        } catch (RuntimeException e) {
            throw new DoesNotExistException("Contract does not exist");
        }
    }
}
