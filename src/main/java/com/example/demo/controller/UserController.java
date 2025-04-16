package com.example.demo.controller;

import com.example.demo.dto.UserCheckListDto;
import com.example.demo.dto.UserDataDto;
import com.example.demo.dto.UserDetailDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.entity.UserData;
import com.example.demo.exception.AlreadyExistsException;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.payload.UserPayload;
import com.example.demo.response.CustomResponse;
import com.example.demo.service.AuthCheckService;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthCheckService authCheck;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil, AuthCheckService authCheck) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authCheck = authCheck;
    }

    // Get all users
    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        List<UserDto> users = userService.getUsers();
        CustomResponse<List<UserDto>> customResponse = new CustomResponse<>(200, users);
        return ResponseEntity.ok(customResponse);
    }

    // Create new user
    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody UserPayload user) {
        try {
            UserDto createdUser = userService.createUser(user);
            CustomResponse<UserDto> successResponse = new CustomResponse<>(200, createdUser);
            return ResponseEntity.ok(successResponse);
        } catch (AlreadyExistsException e) {
            CustomResponse<String> errorResponse = new CustomResponse<>(409, "User already exists. Try another account!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @authCheck.hasPermissionToUpdateOrDeleteUser(#id)")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserPayload userPayload, HttpServletRequest request) {
        try {
            userPayload.setId(id);
            UserDto updatedUser = userService.updateUser(userPayload);
            return ResponseEntity.ok(new CustomResponse<>(200, updatedUser));
        } catch (DoesNotExistException e) {
            CustomResponse<String> errorResponse = new CustomResponse<>(409, "User does not exist. Try another account!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }

    // Find user
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN') or @authCheck.hasPermissionToUpdateOrDeleteUser(#id)")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            UserDto user = userService.getUserById(id);
            CustomResponse<UserDto> customResponse = new CustomResponse<>(200, user);
            return ResponseEntity.ok(customResponse);
        } catch (DoesNotExistException e) {
            CustomResponse<String> errorResponse = new CustomResponse<>(409, "User does not exist");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            UserDto user = userService.deleteUser(id);
            CustomResponse<UserDto> successResponse = new CustomResponse<>(200, user);
            return ResponseEntity.ok(successResponse);
        } catch (DoesNotExistException e) {
            CustomResponse<String> errorResponse = new CustomResponse<>(409, "User does not exist");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }

    // Find User Data
    @GetMapping("/{id}/details")
    @PreAuthorize("hasAnyAuthority('ADMIN') or @authCheck.hasPermissionToUpdateOrDeleteUser(#id)")
    public ResponseEntity<?> getUserData(@PathVariable Long id) {
        try {
            UserDetailDto userDataDto = userService.getUserDetail(id);
            CustomResponse<UserDetailDto> customResponse = new CustomResponse<>(200, userDataDto);
            return ResponseEntity.ok(customResponse);
        } catch (DoesNotExistException e) {
            CustomResponse<String> errorResponse = new CustomResponse<>(409, "User does not exist");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }

    @GetMapping("/{id}/check-list")
    @PreAuthorize("hasAnyAuthority('ADMIN') or @authCheck.hasPermissionToUpdateOrDeleteUser(#id)")
    public ResponseEntity<?> getUserCheckList(@PathVariable Long id) {
        try {
            UserCheckListDto userCheckListDto = userService.getUserCheckListDto(id);
            CustomResponse<UserCheckListDto> customResponse = new CustomResponse<>(200, userCheckListDto);
            return ResponseEntity.ok(customResponse);
        } catch (DoesNotExistException e) {
            CustomResponse<String> errorResponse = new CustomResponse<>(409, "User does not exist");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }
}
