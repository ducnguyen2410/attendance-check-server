package com.example.demo.service;

import com.example.demo.dto.PositionDto;
import com.example.demo.entity.Position;
import com.example.demo.payload.PositionPayload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PositionService {
    public abstract List<PositionDto> getAllPositions();
    public abstract PositionDto createPosition(PositionPayload position);
    public abstract PositionDto getPositionById(Long id);
    public abstract PositionDto updatePosition(PositionPayload position);
    public abstract PositionDto deletePosition(Long id);
}
