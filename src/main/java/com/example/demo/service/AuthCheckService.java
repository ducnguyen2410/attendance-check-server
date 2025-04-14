package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Component("authCheck")
public class AuthCheckService {

    @Autowired
    private UserService userService;
    private JwtUtil jwtUtil;

    public boolean canAccessUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        UserDto userDto = userService.getUserById(userId);
        return currentUsername.equals(userDto.getUsername());
    }

    public boolean canModifyUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));
        if (isAdmin) return true;

        String currentUsername = authentication.getName();
        UserDto userDto = userService.getUserById(userId);
        return currentUsername.equals(userDto.getUsername());
    }

    public boolean hasPermissionToUpdateOrDelete(Long pathId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return false;

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));
        if (isAdmin) return true;

        String token = getTokenFromContext();
        Long tokenUserId = jwtUtil.getUserIdFromToken(token);

        return tokenUserId.equals(pathId);
    }

    private String getTokenFromContext() {
        // Assuming you're using a Bearer token
        var requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes sra) {
            String authHeader = sra.getRequest().getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                return authHeader.substring(7);
            }
        }
        return null;
    }
}
