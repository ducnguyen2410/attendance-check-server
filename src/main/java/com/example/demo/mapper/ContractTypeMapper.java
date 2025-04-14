package com.example.demo.mapper;

import com.example.demo.dto.ContractTypeDto;
import com.example.demo.entity.ContractType;
import com.example.demo.payload.ContractPayload;
import com.example.demo.payload.ContractTypePayload;

public class ContractTypeMapper {
    public static ContractType toContractType(ContractTypePayload contractType) {
        return ContractType.builder()
                .contractType(contractType.getContractType())
                .build();
    }

    public static void mapContractType(ContractType contractType, ContractTypePayload contractTypePayload) {
        if(contractTypePayload.getContractType() != null) contractType.setContractType(contractTypePayload.getContractType());
    }

    public static ContractTypeDto toContractTypeDto(ContractType contractType) {
        return ContractTypeDto.builder()
                .id(contractType.getId())
                .contractType(contractType.getContractType())
                .createTime(contractType.getCreateTime())
                .updateTime(contractType.getUpdateTime())
                .build();
    }
}
