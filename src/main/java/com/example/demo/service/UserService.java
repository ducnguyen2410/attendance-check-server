package com.example.demo.service;

import com.example.demo.dto.UserCheckListDto;
import com.example.demo.dto.UserDetailDto;
import com.example.demo.dto.UserDataDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.payload.UserPayload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public abstract UserDto createUser(UserPayload user);
    public abstract UserDto updateUser(UserPayload user);
    public abstract UserDto deleteUser(Long id);
    public abstract UserDto getUserByUserName(String username);
    public abstract UserDto getUserById(Long id);
    public abstract List<UserDto> getUsers();
    public abstract UserDataDto getUserDataByUserId(Long id);
    public abstract UserDto updateUserStatus(Long id);
    public abstract UserDetailDto getUserDetail(Long id);
    public abstract UserCheckListDto getUserCheckListDto(Long id);
}
