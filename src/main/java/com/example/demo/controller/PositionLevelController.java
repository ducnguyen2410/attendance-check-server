package com.example.demo.controller;

import com.example.demo.dto.PositionLevelDto;
import com.example.demo.entity.PositionLevel;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.payload.PositionLevelPayload;
import com.example.demo.response.CustomResponse;
import com.example.demo.service.PositionLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/position-level")
public class PositionLevelController {

    private final PositionLevelService positionLevelService;

    @Autowired
    public PositionLevelController(PositionLevelService positionLevelService) {
        this.positionLevelService = positionLevelService;
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<?> getAllPositionLevels() {
        List<PositionLevelDto> positionLevels = positionLevelService.getPositionLevels();
        CustomResponse<List<PositionLevelDto>> successResponse = new CustomResponse<>(200, positionLevels);
        return ResponseEntity.ok(successResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<?> getPositionLevelById(@PathVariable Long id) {
        PositionLevelDto positionLevel = positionLevelService.getPositionLevelById(id);
        CustomResponse<PositionLevelDto> successResponse = new CustomResponse<>(200, positionLevel);
        return ResponseEntity.ok(successResponse);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createPositionLevel(@RequestBody PositionLevelPayload positionLevel) {
        PositionLevelDto newPositionLevel = positionLevelService.createPositionLevel(positionLevel);
        CustomResponse<PositionLevelDto> successResponse = new CustomResponse<>(200, newPositionLevel);
        return ResponseEntity.ok(successResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updatePositionLevel(@PathVariable Long id, @RequestBody PositionLevelPayload positionLevelPayload) {
        try {
            positionLevelPayload.setId(id);
            PositionLevelDto updatedPositionLevel = positionLevelService.updatePositionLevel(positionLevelPayload);
            CustomResponse<PositionLevelDto> successResponse = new CustomResponse<>(200, updatedPositionLevel);
            return ResponseEntity.ok(successResponse);
        } catch (DoesNotExistException e) {
            CustomResponse<String> errorResponse = new CustomResponse<>(404, "Position Level does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deletePositionLevel(@PathVariable Long id) {
        try {
            PositionLevelDto deletedPositionLevel = positionLevelService.deletePositionLevel(id);
            CustomResponse<PositionLevelDto> successResponse = new CustomResponse<>(200, deletedPositionLevel);
            return ResponseEntity.ok(successResponse);
        } catch (DoesNotExistException e) {
            CustomResponse<String> errorResponse = new CustomResponse<>(404, "Position Level does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
