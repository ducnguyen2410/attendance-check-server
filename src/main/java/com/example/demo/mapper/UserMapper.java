package com.example.demo.mapper;

import com.example.demo.dto.*;
import com.example.demo.entity.Attendance;
import com.example.demo.entity.Overtime;
import com.example.demo.entity.User;
import com.example.demo.payload.UserPayload;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static User toUser(UserPayload userPayload) {
        return User.builder()
                .username(userPayload.getUsername())
                .password(userPayload.getPassword())
                .email(userPayload.getEmail())
                .build();
    }

    public static void mapToUser(UserPayload userPayload, User user) {
        if(userPayload.getPassword() != null) user.setPassword(userPayload.getPassword());
        if(userPayload.getEmail() != null) user.setEmail(userPayload.getEmail());
    }

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus())
                .position(user.getPosition() != null ? user.getPosition().getPositionName() : null)
                .positionLevel(user.getPositionLevel() != null ? user.getPositionLevel().getPositionLevelName() : null)
                .userData(user.getUserData() != null ? UserDataMapper.toUserDataDto(user.getUserData()) : null)
                .contract(user.getContract() != null ? user.getContract().getContractName() : null)
                .createdAt(user.getCreatedAt())
                .roles(user.getRoles().stream().map(RoleMapper::toRoleDto).collect(Collectors.toSet()))
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static UserDetailDto toUserDetailDto(User user) {
        return UserDetailDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .position(user.getEmail())
                .positionLevel(user.getPositionLevel() != null ? user.getPositionLevel().getPositionLevelName() : null)
                .userData(user.getUserData() != null ? UserDataMapper.toUserDataDto(user.getUserData()) : null)
                .build();
    }

    public static UserCheckListDto toUserCheckListDto(User user, List<AttendanceDto> attendances, List<OvertimeDto> overtimes) {
        return UserCheckListDto.builder()
                .username(user.getUsername())
                .userId(user.getId())
                .attendances(attendances)
                .overtimes(overtimes)
                .build();
    }
}
