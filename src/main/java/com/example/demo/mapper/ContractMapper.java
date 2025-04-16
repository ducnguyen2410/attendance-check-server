package com.example.demo.mapper;

import com.example.demo.dto.ContractDto;
import com.example.demo.entity.Contract;
import com.example.demo.entity.ContractType;
import com.example.demo.entity.User;
import com.example.demo.payload.ContractPayload;
import com.example.demo.repository.ContractTypeRepository;
import com.example.demo.util.DateUtil;

public class ContractMapper {
    public static Contract toContract(ContractPayload contract, ContractType contractType) {
        return Contract.builder()
                .contractName(contract.getContractName())
                .contractType(contractType)
                .startHour(DateUtil.toLocalTime(contract.getStartHour()))
                .endHour(DateUtil.toLocalTime(contract.getEndHour()))
                .startDate(DateUtil.toLocalDate(contract.getStartDate()))
                .endDate(DateUtil.toLocalDate(contract.getEndDate()))
                .build();
    }

    public static void mapContract(Contract contract, ContractPayload contractPayload, ContractType contractType) {
        if(contractPayload.getContractName() != null) contract.setContractName(contractPayload.getContractName());
        contract.setContractType(contractType);
        if(contractPayload.getEndHour() != null) contract.setEndHour(DateUtil.toLocalTime(contractPayload.getEndHour()));
        if(contractPayload.getStartHour() != null) contract.setStartHour(DateUtil.toLocalTime(contractPayload.getStartHour()));
        // Enable validate start date and end date in date util
    }

    public static ContractDto toContractDto(Contract contract, User user) {
        return ContractDto.builder()
                .id(contract.getId())
                .contractName(contract.getContractName())
                .contractType(ContractTypeMapper.toContractTypeDto(contract.getContractType()))
                .userId(user.getId())
                .startHour(contract.getStartHour())
                .endHour(contract.getEndHour())
                .startDate(contract.getStartDate())
                .endDate(contract.getEndDate())
                .createTime(contract.getCreateTime())
                .updateTime(contract.getUpdateTime())
                .build();
    }
}
