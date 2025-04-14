package com.example.demo.service.impl;

import com.example.demo.dto.PositionDto;
import com.example.demo.entity.Position;
import com.example.demo.entity.User;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.mapper.PositionMapper;
import com.example.demo.payload.PositionPayload;
import com.example.demo.repository.PositionRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PositionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PositionImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final UserRepository userRepository;

    public PositionImpl(PositionRepository positionRepository, UserRepository userRepository) {
        this.positionRepository = positionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<PositionDto> getAllPositions() {
        List<Position> positions = positionRepository.findAll();
        List<PositionDto> positionDtoList = new ArrayList<>();
        for (Position position : positions) {
            positionDtoList.add(PositionMapper.toPositionDto(position));
        }
        return positionDtoList;
    }

    @Override
    public PositionDto createPosition(PositionPayload position) {
        Position newPosition = PositionMapper.toPosition(position);
        positionRepository.save(newPosition);
        return PositionMapper.toPositionDto(newPosition);
    }

    @Override
    public PositionDto getPositionById(Long id) {
        Position position = positionRepository.findById(id).orElse(null);
        if (position == null) return null;
        return PositionMapper.toPositionDto(position);
    }

    @Override
    public PositionDto updatePosition(PositionPayload positionPayload) {
        Position position = positionRepository.findById(positionPayload.getId()).orElse(null);
        if (position == null) {
            throw new DoesNotExistException("Position not found");
        }
        PositionMapper.mapPosition(position, positionPayload);
        positionRepository.save(position);
        return PositionMapper.toPositionDto(position);
    }

    @Override
    public PositionDto deletePosition(Long id) {
        Position position = positionRepository.findById(id).orElse(null);
        if (position == null) {
            throw new DoesNotExistException("Position not found");
        }
        List<User> users = userRepository.findUserByPosition(position);
        for (User user : users) {
            user.setPosition(null);
        }
        userRepository.saveAll(users);
        positionRepository.deleteById(id);
        return PositionMapper.toPositionDto(position);
    }
}
