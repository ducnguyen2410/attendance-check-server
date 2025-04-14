package com.example.demo.service;

import com.example.demo.dto.PositionLevelDto;
import com.example.demo.entity.PositionLevel;
import com.example.demo.payload.PositionLevelPayload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PositionLevelService {
    public abstract List<PositionLevelDto> getPositionLevels();
    public abstract PositionLevelDto getPositionLevelById(Long id);
    public abstract PositionLevelDto createPositionLevel(PositionLevelPayload positionLevel);
    public abstract PositionLevelDto updatePositionLevel(PositionLevelPayload positionLevel);
    public abstract PositionLevelDto deletePositionLevel(Long id);
}
