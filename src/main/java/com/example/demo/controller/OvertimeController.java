package com.example.demo.controller;

import com.example.demo.dto.OvertimeDto;
import com.example.demo.entity.Overtime;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.payload.OvertimePayload;
import com.example.demo.dto.OvertimeUserDto;
import com.example.demo.response.CustomResponse;
import com.example.demo.service.OvertimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/overtime")
public class OvertimeController {

    private final OvertimeService overtimeService;

    public OvertimeController(OvertimeService overtimeService) {
        this.overtimeService = overtimeService;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getOvertimes() {
        List<OvertimeDto> overtimes = overtimeService.getOvertimes();
        CustomResponse<List<OvertimeDto>> customResponse = new CustomResponse<>(200, overtimes);
        return ResponseEntity.ok(customResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @authCheck.hasPermissionToUpdateOrDelete(#id)")
    public ResponseEntity<?> getOvertimeById(@PathVariable Long id) {
        OvertimeDto overtime = overtimeService.getOvertimeById(id);
        CustomResponse<OvertimeDto> customResponse = new CustomResponse<>(200, overtime);
        return ResponseEntity.ok(customResponse);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createOvertime(@RequestBody OvertimePayload overtimePayload) {
        try {
            OvertimeDto overtime = overtimeService.createOvertime(overtimePayload);
            CustomResponse<OvertimeDto> customResponse = new CustomResponse<>(200, overtime);
            return ResponseEntity.ok(customResponse);
        } catch (DoesNotExistException e) {
            CustomResponse<String> response = new CustomResponse<>(404, "Error creating overtime.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateOvertime(@PathVariable Long id, @RequestBody OvertimePayload overtimePayload) {
        try {
            overtimePayload.setId(id);
            OvertimeDto overtime = overtimeService.updateOvertime(overtimePayload);
            CustomResponse<OvertimeDto> customResponse = new CustomResponse<>(200, overtime);
            return ResponseEntity.ok(customResponse);
        } catch (DoesNotExistException e) {
            CustomResponse<String> response = new CustomResponse<>(404, "Error updating overtime.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteOvertime(@PathVariable Long id) {
        try {
            OvertimeDto overtime = overtimeService.deleteOvertime(id);
            CustomResponse<OvertimeDto> customResponse = new CustomResponse<>(200, overtime);
            return ResponseEntity.ok(customResponse);
        } catch (DoesNotExistException e) {
            CustomResponse<String> response = new CustomResponse<>(404, "Error deleting overtime.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Find overtimes by user id
    @GetMapping("/user-id/{id}")
    public ResponseEntity<?> getOvertimeByUserId(@PathVariable Long id) {
        List<OvertimeUserDto> overtimeUserDtoList = overtimeService.getOvertimeByUserId(id);
        CustomResponse<List<OvertimeUserDto>> customResponse = new CustomResponse<>(200, overtimeUserDtoList);
        return ResponseEntity.ok(customResponse);
    }
}
