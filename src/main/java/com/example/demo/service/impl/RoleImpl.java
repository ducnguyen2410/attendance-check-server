package com.example.demo.service.impl;

import com.example.demo.dto.RoleDto;
import com.example.demo.entity.Role;
import com.example.demo.exception.AlreadyExistsException;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.payload.RolePayload;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;

import java.util.ArrayList;
import java.util.List;

public class RoleImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        List<RoleDto> roleDtoList = new ArrayList<>();
        for (Role role : roles) {
            roleDtoList.add(RoleMapper.toRoleDto(role));
        }
        return roleDtoList;
    }

    @Override
    public RoleDto getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role == null) return null;
        return RoleMapper.toRoleDto(role);
    }

    @Override
    public RoleDto getRoleByName(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) return null;
        return RoleMapper.toRoleDto(role);
    }

    @Override
    public RoleDto createRole(RolePayload rolePayload) {
        Role role = roleRepository.findByName(rolePayload.getName());
        if (role != null) {
            throw new AlreadyExistsException("Role already exists");
        }
        role = RoleMapper.toRole(rolePayload);
        roleRepository.save(role);
        return RoleMapper.toRoleDto(role);
    }

    @Override
    public RoleDto updateRole(RolePayload rolePayload) {
        Role role = roleRepository.findById(rolePayload.getId()).orElse(null);
        if (role == null) throw new DoesNotExistException("Role not found");
        RoleMapper.mapToRole(role, rolePayload);
        roleRepository.save(role);
        return RoleMapper.toRoleDto(role);
    }

    @Override
    public RoleDto deleteRole(Long id) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role == null) throw new DoesNotExistException("Role not found");
        roleRepository.delete(role);
        return RoleMapper.toRoleDto(role);
    }
}
