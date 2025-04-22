package com.example.demo.service;

import com.example.demo.dto.UserDataDto;
import com.example.demo.entity.UserData;
import com.example.demo.payload.UserDataPayload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserDataService {
    public abstract List<UserDataDto> getUserData();
    public abstract UserDataDto getUserDataById(Long id);
    public abstract UserDataDto updateUserData(UserDataPayload userData);
    public abstract UserDataDto createUserData(UserDataPayload userData);
    public abstract UserDataDto deleteUserData(Long id);
}
