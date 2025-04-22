package com.example.demo.service;

import com.example.demo.dto.RoleDto;
import com.example.demo.payload.RolePayload;

import java.util.List;

public interface RoleService {
    public abstract List<RoleDto> getAllRoles();
    public abstract RoleDto getRoleById(Long id);
    public abstract RoleDto getRoleByName(String name);
    public abstract RoleDto createRole(RolePayload rolePayload);
    public abstract RoleDto updateRole(RolePayload rolePayload);
    public abstract RoleDto deleteRole(Long id);
}
