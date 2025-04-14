package com.example.demo.controller;

import com.example.demo.dto.UserDataDto;
import com.example.demo.entity.UserData;
import com.example.demo.exception.AlreadyExistsException;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.payload.UserDataPayload;
import com.example.demo.response.CustomResponse;
import com.example.demo.service.UserDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-data")
public class UserDataController {
    private final UserDataService userDataService;

    public UserDataController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getUserData() {
        List<UserDataDto> userDataList = userDataService.getUserData();
        CustomResponse<List<UserDataDto>> customResponse = new CustomResponse<>(200, userDataList);
        return ResponseEntity.ok(customResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER') and @authCheck.hasPermissionToUpdateOrDelete(#id)")
    public ResponseEntity<?> getUserDataById(@PathVariable Long id) {
        UserDataDto userData = userDataService.getUserDataById(id);
        CustomResponse<UserDataDto> customResponse = new CustomResponse<>(200, userData);
        return ResponseEntity.ok(customResponse);
    }

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @authCheck.hasPermissionToUpdateOrDelete(#userDataPayload.userId)")
    public ResponseEntity<?> createUserData(@RequestBody UserDataPayload userDataPayload) {
        try {
            UserDataDto userData = userDataService.createUserData(userDataPayload);
            CustomResponse<UserDataDto> customResponse = new CustomResponse<>(200, userData);
            return ResponseEntity.ok(customResponse);
        } catch (AlreadyExistsException e) {
            CustomResponse<String> customResponse = new CustomResponse<>(409, "User already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(customResponse);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER') and @authCheck.hasPermissionToUpdateOrDelete(#id)")
    public ResponseEntity<?> updateUserData(@PathVariable Long id, @RequestBody UserDataPayload userDataPayload) {
        try {
            userDataPayload.setId(id);
            UserDataDto userData = userDataService.updateUserData(userDataPayload);
            CustomResponse<UserDataDto> customResponse = new CustomResponse<>(200, userData);
            return ResponseEntity.ok(customResponse);
        } catch (AlreadyExistsException e) {
            CustomResponse<String> customResponse = new CustomResponse<>(409, "User already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(customResponse);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @authCheck.hasPermissionToUpdateOrDelete(#id)")
    public ResponseEntity<?> deleteUserData(@PathVariable Long id) {
        try {
            UserDataDto userData = userDataService.deleteUserData(id);
            CustomResponse<UserDataDto> customResponse = new CustomResponse<>(200, userData);
            return ResponseEntity.ok(customResponse);
        } catch (DoesNotExistException e) {
            CustomResponse<String> customResponse = new CustomResponse<>(404, "User does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customResponse);
        }
    }
}
