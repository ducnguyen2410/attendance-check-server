package com.example.demo.service.impl;

import com.example.demo.dto.UserDataDto;
import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.example.demo.entity.UserData;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.exception.IDNotMatchData;
import com.example.demo.mapper.UserDataMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.payload.UserDataPayload;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.UserDataRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserDataService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserDataImpl implements UserDataService {

    private final UserDataRepository userDataRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public UserDataImpl(UserDataRepository userDataRepository, UserRepository userRepository, AddressRepository addressRepository) {
        this.userDataRepository = userDataRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public List<UserDataDto> getUserData() {
        List<UserData> userDataList = userDataRepository.findAll();
        List<UserDataDto> userDataDtoList = new ArrayList<>();
        for (UserData userData : userDataList) {
            UserDataDto userDataDto = UserDataMapper.toUserDataDto(userData);
            userDataDtoList.add(userDataDto);
        }
        return userDataDtoList;
    }

    @Override
    public UserDataDto getUserDataById(Long id) {
        UserData userData = userDataRepository.findById(id).orElse(null);
        if (userData == null) return null;
        return UserDataMapper.toUserDataDto(userData);
    }

    @Override
    public UserDataDto updateUserData(UserDataPayload userDataPayload) {
        UserData userData = userDataRepository.findById(userDataPayload.getId())
                .orElseThrow(() -> new DoesNotExistException("User data not found"));
        User user = userData.getUser();
        UserDataMapper.mapUserData(userData, userDataPayload);
        userDataRepository.save(userData);
        return UserDataMapper.toUserDataDto(userData);
    }

    @Override
    public UserDataDto createUserData(UserDataPayload userDataPayload) {
        User user = userRepository.findById(userDataPayload.getUserId()).orElse(null);
        if (user == null) {
            throw new IDNotMatchData("User and user data not match");
        }
        if (user.getUserData() != null) {
            throw new IDNotMatchData("User data already exist");
        }
        UserData userData = UserDataMapper.toUserData(userDataPayload, user);
        userDataRepository.save(userData);
        return UserDataMapper.toUserDataDto(userData);
    }

    @Override
    public UserDataDto deleteUserData(Long id) {
        UserData userData = userDataRepository.findById(id)
                .orElseThrow(() -> new DoesNotExistException("User data not found"));
        User user = userData.getUser();
        if (user != null) {
            user.setUserData(null);
            userRepository.save(user);
        }
        if(userData.getAddress() != null) {
            addressRepository.deleteById(userData.getAddress().getId());
        }
        userDataRepository.delete(userData);
        return UserDataMapper.toUserDataDto(userData);
    }
}
