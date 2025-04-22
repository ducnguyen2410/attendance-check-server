package com.example.demo.mapper;

import com.example.demo.dto.UserDataDto;
import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.example.demo.entity.UserData;
import com.example.demo.payload.UserDataPayload;
import com.example.demo.util.DateUtil;

import java.time.LocalDate;

public class UserDataMapper {
    public static UserData toUserData(UserDataPayload userDataPayload, User user) {
        UserData userData = UserData.builder()
                .firstName(userDataPayload.getFirstName())
                .lastName(userDataPayload.getLastName())
                .birthDate(DateUtil.toLocalDate(userDataPayload.getBirthDate()))
                .phone(userDataPayload.getPhone())
                .user(user)
                .address(userDataPayload.getAddress() != null ? AddressMapper.toAddress(userDataPayload.getAddress(), user.getUserData()) : null)
                .build();
        user.setUserData(userData);
        return userData;
    }

    public static void mapUserData(UserData userData, UserDataPayload userDataPayload) {
        if(userDataPayload.getFirstName() != null) userData.setFirstName(userDataPayload.getFirstName());
        if(userDataPayload.getLastName() != null) userData.setLastName(userDataPayload.getLastName());
        if(userDataPayload.getBirthDate() != null) userData.setBirthDate(DateUtil.toLocalDate(userDataPayload.getBirthDate()));
        if(userDataPayload.getPhone() != null) userData.setPhone(userDataPayload.getPhone());
        if (userDataPayload.getAddress() != null) {
            if (userData.getAddress() == null) {
                Address address = AddressMapper.toAddress(userDataPayload.getAddress(), userData);
                userData.setAddress(address);
                address.setUserData(userData);
            } else {
                AddressMapper.mapPayloadToAddress(userDataPayload.getAddress(), userData.getAddress());
            }
        }
    }

    public static UserDataDto toUserDataDto(UserData userData) {
        return UserDataDto.builder()
                .id(userData.getId())
                .userId(userData.getUser().getId())
                .firstName(userData.getFirstName())
                .lastName(userData.getLastName())
                .phone(userData.getPhone())
                .birthDate(userData.getBirthDate())
                .address(userData.getAddress() != null ? AddressMapper.toAddressDto(userData.getAddress()) : null)
                .createdAt(userData.getCreatedAt())
                .updatedAt(userData.getUpdatedAt())
                .build();
    }
}
