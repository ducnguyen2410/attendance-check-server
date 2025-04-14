package com.example.demo.controller;

import com.example.demo.dto.OvertimeSalaryDto;
import com.example.demo.entity.OvertimeSalary;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.payload.OvertimeSalaryPayload;
import com.example.demo.response.CustomResponse;
import com.example.demo.service.OvertimeSalaryService;
import com.example.demo.service.OvertimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/overtime-salary")
public class OvertimeSalaryController {

    private final OvertimeSalaryService overtimeSalaryService;

    public OvertimeSalaryController(OvertimeSalaryService overtimeSalaryService) {
        this.overtimeSalaryService = overtimeSalaryService;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getOvertimeSalaries() {
        List<OvertimeSalaryDto> overtimeSalaries = overtimeSalaryService.getAllOvertimeSalary();
        CustomResponse<List<OvertimeSalaryDto>> response = new CustomResponse<>(200, overtimeSalaries);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getOvertimeSalaryById(@PathVariable  Long id) {
        OvertimeSalaryDto overtimeSalary = overtimeSalaryService.getOvertimeSalaryById(id);
        CustomResponse<OvertimeSalaryDto> response = new CustomResponse<>(200, overtimeSalary);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createOvertimeSalary(@RequestBody OvertimeSalaryPayload overtimeSalaryPayload) {
        OvertimeSalaryDto overtimeSalary = overtimeSalaryService.createOvertimeSalary(overtimeSalaryPayload);
        CustomResponse<OvertimeSalaryDto> response = new CustomResponse<>(200, overtimeSalary);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateOvertimeSalary(@PathVariable Long id, @RequestBody OvertimeSalaryPayload overtimeSalaryPayload) {
        try {
            overtimeSalaryPayload.setId(id);
            OvertimeSalaryDto overtimeSalary = overtimeSalaryService.updateOvertimeSalary(overtimeSalaryPayload);
            CustomResponse<OvertimeSalaryDto> response = new CustomResponse<>(200, overtimeSalary);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DoesNotExistException e) {
            CustomResponse<String> response = new CustomResponse<>(404, "Error updating overtime salary");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteOvertimeSalary(@PathVariable Long id) {
        try {
            OvertimeSalaryDto overtimeSalary = overtimeSalaryService.deleteOvertimeSalary(id);
            CustomResponse<OvertimeSalaryDto> response = new CustomResponse<>(200, overtimeSalary);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DoesNotExistException e) {
            CustomResponse<String> response = new CustomResponse<>(404, "Error deleting overtime salary");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
