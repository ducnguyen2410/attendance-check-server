package com.example.demo.repository;

import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public abstract Role findByName(String name);
}
