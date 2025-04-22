package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.*;
import com.example.demo.payload.UserDataPayload;
import com.example.demo.repository.*;
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
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserDataRepository userDataRepository;
    @Autowired
    private OvertimeRepository overtimeRepository;

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

    public boolean hasPermissionToUpdateOrDeleteUser(Long pathId) {
        String token = getTokenFromContext();
        Long tokenUserId = jwtUtil.getUserIdFromToken(token);
        return tokenUserId.equals(pathId);
    }

    public boolean hasPermissionToViewOrUpdateOvertime(Long overtimeId) {
        String token = getTokenFromContext();
        Long tokenUserId = jwtUtil.getUserIdFromToken(token);
        Overtime overtime = overtimeRepository.findById(overtimeId).orElse(null);
        if (overtime != null) {
            return overtime.getUser().getId().equals(tokenUserId);
        }
        return false;
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

    public boolean hasPermissionToViewOrUpdateContract(Long contractId) {
        String token = getTokenFromContext();
        Long tokenUserId = jwtUtil.getUserIdFromToken(token);
        Contract contract = contractRepository.findById(contractId).orElse(null);
        if (contract != null) {
            return contract.getUser().getId().equals(tokenUserId);
        }
        return false;
    }

    public boolean hasPermissionToViewOrUpdateUserData(Long userDataId) {
        String token = getTokenFromContext();
        Long tokenUserId = jwtUtil.getUserIdFromToken(token);
        UserData userData = userDataRepository.findById(userDataId).orElse(null);
        if (userData != null) {
            return userData.getUser().getId().equals(tokenUserId);
        }
        return false;
    }

    public boolean hasPermissionToViewOrUpdateAttendance(Long attendanceId) {
        String token = getTokenFromContext();
        Long tokenUserId = jwtUtil.getUserIdFromToken(token);
        Attendance attendance = attendanceRepository.findById(attendanceId).orElse(null);
        if (attendance != null) {
            return attendance.getUser().getId().equals(tokenUserId);
        }
        return false;
    }

    public boolean hasPermissionToViewOrUpdateAddress(Long addressId) {
        String token = getTokenFromContext();
        Long tokenUserId = jwtUtil.getUserIdFromToken(token);
        Address address = addressRepository.findById(addressId).orElse(null);
        if (address != null) {
            return address.getUserData().getUser().getId().equals(tokenUserId);
        }
        return false;
    }
}
