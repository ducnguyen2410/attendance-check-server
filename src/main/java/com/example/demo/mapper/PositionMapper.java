package com.example.demo.mapper;

import com.example.demo.dto.PositionDto;
import com.example.demo.entity.Position;
import com.example.demo.payload.PositionPayload;

public class PositionMapper {
    public static Position toPosition (PositionPayload position) {
        return Position.builder()
                .positionName(position.getPositionName())
                .baseSalary(position.getBaseSalary())
                .build();
    }

    public static void mapPosition(Position position, PositionPayload positionPayload) {
        if(positionPayload.getBaseSalary() != null) {
            position.setBaseSalary(positionPayload.getBaseSalary());
        }
        if(positionPayload.getPositionName() != null) {
            position.setPositionName(positionPayload.getPositionName());
        }
    }

    public static PositionDto toPositionDto(Position position) {
        return PositionDto.builder()
                .id(position.getId())
                .positionName(position.getPositionName())
                .baseSalary(position.getBaseSalary())
                .createdTime(position.getCreatedTime())
                .updatedTime(position.getUpdatedTime())
                .build();
    }
}
