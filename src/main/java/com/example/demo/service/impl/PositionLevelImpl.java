package com.example.demo.service.impl;

import com.example.demo.dto.PositionLevelDto;
import com.example.demo.entity.PositionLevel;
import com.example.demo.entity.User;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.mapper.PositionLevelMapper;
import com.example.demo.payload.PositionLevelPayload;
import com.example.demo.repository.PositionLevelRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PositionLevelService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PositionLevelImpl implements PositionLevelService {

    private final PositionLevelRepository positionLevelRepository;
    private final UserRepository userRepository;

    public PositionLevelImpl(PositionLevelRepository positionLevelRepository, UserRepository userRepository) {
        this.positionLevelRepository = positionLevelRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<PositionLevelDto> getPositionLevels() {
        List<PositionLevel> positionLevels = positionLevelRepository.findAll();
        List<PositionLevelDto> positionLevelDtoList = new ArrayList<>();
        for (PositionLevel positionLevel : positionLevels) {
            PositionLevelDto positionLevelDto = PositionLevelMapper.toPositionLevelDto(positionLevel);
            positionLevelDtoList.add(positionLevelDto);
        }
        return positionLevelDtoList;
    }

    @Override
    public PositionLevelDto getPositionLevelById(Long id) {
        PositionLevel positionLevel = positionLevelRepository.findById(id).orElse(null);
        if (positionLevel == null) return null;
        return PositionLevelMapper.toPositionLevelDto(positionLevel);
    }

    @Override
    public PositionLevelDto createPositionLevel(PositionLevelPayload positionLevelPayload) {
        PositionLevel newPositionLevel = PositionLevelMapper.toPositionLevel(positionLevelPayload);
        positionLevelRepository.save(newPositionLevel);
        return PositionLevelMapper.toPositionLevelDto(newPositionLevel);
    }

    @Override
    public PositionLevelDto updatePositionLevel(PositionLevelPayload positionLevelPayload) {
        PositionLevel positionLevel = positionLevelRepository.findById(positionLevelPayload.getId()).orElse(null);
        if (positionLevel == null) {
            throw new DoesNotExistException("Position Level does not exist");
        }
        PositionLevelMapper.mapPositionLevel(positionLevel, positionLevelPayload);
        positionLevelRepository.save(positionLevel);
        return PositionLevelMapper.toPositionLevelDto(positionLevel);
    }

    @Override
    public PositionLevelDto deletePositionLevel(Long id) {
        try {
            PositionLevel positionLevel = positionLevelRepository.findById(id).orElse(null);
            if (positionLevel == null) {
                throw new DoesNotExistException("Position Level does not exist");
            }
            List<User> users = userRepository.findUserByPositionLevel(positionLevel);
            for (User user : users) {
                user.setPositionLevel(null);
            }
            userRepository.saveAll(users);
            positionLevelRepository.deleteById(id);
            return PositionLevelMapper.toPositionLevelDto(positionLevel);
        } catch (RuntimeException e) {
            throw new DoesNotExistException("Position Level not found");
        }
    }
}
