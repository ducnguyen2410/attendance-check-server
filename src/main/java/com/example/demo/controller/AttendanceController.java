package com.example.demo.controller;

import com.example.demo.dto.AttendanceDto;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.payload.AttendancePayload;
import com.example.demo.response.CustomResponse;
import com.example.demo.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllAttendance() {
        CustomResponse<List<AttendanceDto>> response = new CustomResponse<>(200, attendanceService.getAllAttendance());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN') or @authCheck.hasPermissionToViewOrUpdateAttendance(#id)")
    public ResponseEntity<?> getAttendanceById(@PathVariable Long id) {
        CustomResponse<AttendanceDto> response = new CustomResponse<>(200, attendanceService.getAttendanceById(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN') or @authCheck.hasPermissionToViewOrUpdateAttendance(#attendancePayload.userId)")
    public ResponseEntity<?> createAttendance(@RequestBody AttendancePayload attendancePayload) {
        AttendanceDto newAttendance = attendanceService.createAttendance(attendancePayload);
        CustomResponse<AttendanceDto> response =  new CustomResponse<>(200, newAttendance);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @authCheck.hasPermissionToViewOrUpdateAttendance(#id)")
    public ResponseEntity<?> updateAttendance(@PathVariable Long id, @RequestBody AttendancePayload attendancePayload) {
        try {
            AttendanceDto updatedAttendance = attendanceService.updateAttendance(attendancePayload);
            CustomResponse<AttendanceDto> response =  new CustomResponse<>(200, updatedAttendance);
            return ResponseEntity.ok(response);
        } catch (DoesNotExistException e) {
            CustomResponse<String> response =  new CustomResponse<>(404, "Tardy not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteAttendance(@PathVariable Long id) {
        try {
            AttendanceDto deletedAttendance = attendanceService.deleteAttendance(id);
            CustomResponse<AttendanceDto> response =  new CustomResponse<>(200, deletedAttendance);
            return ResponseEntity.ok(response);
        } catch (DoesNotExistException e) {
            CustomResponse<String> response =  new CustomResponse<>(404, "Tardy not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
