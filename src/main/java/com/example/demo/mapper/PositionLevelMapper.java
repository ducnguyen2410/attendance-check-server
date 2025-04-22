package com.example.demo.mapper;

import com.example.demo.dto.PositionLevelDto;
import com.example.demo.entity.PositionLevel;
import com.example.demo.payload.PositionLevelPayload;

public class PositionLevelMapper {
    public static PositionLevel toPositionLevel(PositionLevelPayload positionLevelPayload) {
        return PositionLevel.builder()
                .positionLevelName(positionLevelPayload.getPositionLevelName())
                .baseSalary(positionLevelPayload.getBaseSalary())
                .build();
    }

    public static void mapPositionLevel(PositionLevel positionLevel, PositionLevelPayload positionLevelPayload) {
        if(positionLevelPayload.getPositionLevelName() != null) positionLevel.setPositionLevelName(positionLevelPayload.getPositionLevelName());
        if(positionLevelPayload.getBaseSalary() != null) positionLevel.setBaseSalary(positionLevelPayload.getBaseSalary());
    }

    public static PositionLevelDto toPositionLevelDto(PositionLevel positionLevel) {
        return PositionLevelDto.builder()
                .id(positionLevel.getId())
                .positionLevelName(positionLevel.getPositionLevelName())
                .baseSalary(positionLevel.getBaseSalary())
                .createdTime(positionLevel.getCreatedTime())
                .updatedTime(positionLevel.getUpdatedTime())
                .build();
    }
}
