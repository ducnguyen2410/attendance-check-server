package com.example.demo.dto;

import com.example.demo.entity.Role;
import com.example.demo.entity.UserData;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private Boolean status;
    private String email;
    private String position;
    private String positionLevel;
    private UserDataDto userData;
    private Set<RoleDto> roles;
    private String contract;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
