package com.example.demo.controller;

import com.example.demo.dto.PositionDto;
import com.example.demo.entity.Position;
import com.example.demo.entity.User;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.payload.PositionPayload;
import com.example.demo.response.CustomResponse;
import com.example.demo.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController {
    private final PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }


    // Get all positions
    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllPositions() {
        List<PositionDto> positions = positionService.getAllPositions();
        CustomResponse<List<PositionDto>> successResponse = new CustomResponse<>(200, positions);
        return ResponseEntity.ok(successResponse);
    }

    // Get a specific position
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getPositionById(@PathVariable("id") Long id) {
        PositionDto position = positionService.getPositionById(id);
        CustomResponse<PositionDto> successResponse = new CustomResponse<>(200, position);
        return ResponseEntity.ok(successResponse);
    }

    // Create a new position
    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createPosition(@RequestBody PositionPayload position) {
        PositionDto newPosition = positionService.createPosition(position);
        CustomResponse<PositionDto> successResponse = new CustomResponse<>(200, newPosition);
        return ResponseEntity.ok(successResponse);
    }

    // Update a specific position
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updatePosition(@PathVariable Long id, @RequestBody PositionPayload positionPayload) {
        try {
            positionPayload.setId(id);
            PositionDto position = positionService.updatePosition(positionPayload);
            CustomResponse<PositionDto> successResponse = new CustomResponse<>(200, position);
            return ResponseEntity.ok(successResponse);
        } catch (DoesNotExistException e) {
            CustomResponse<String> errorResponse = new CustomResponse<>(404, "Position not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    // Delete a specific position
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deletePosition(@PathVariable Long id) {
        try {
            PositionDto position = positionService.deletePosition(id);
            CustomResponse<PositionDto> successResponse = new CustomResponse<>(200, position);
            return ResponseEntity.ok(successResponse);
        } catch (DoesNotExistException e) {
            CustomResponse<String> errorResponse = new CustomResponse<>(404, "Position not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
