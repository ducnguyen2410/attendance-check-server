package com.example.demo.mapper;

import com.example.demo.dto.RoleDto;
import com.example.demo.entity.Role;
import com.example.demo.payload.RolePayload;

public class RoleMapper {
    public static Role toRole(RolePayload rolePayload) {
        return Role.builder()
                .name(rolePayload.getName())
                .build();
    }

    public static void mapToRole(Role role, RolePayload rolePayload) {
        if (rolePayload.getName() != null) role.setName(rolePayload.getName());
    }

    public static RoleDto toRoleDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
