package com.example.demo.controller;

import com.example.demo.dto.ContractTypeDto;
import com.example.demo.entity.ContractType;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.payload.ContractTypePayload;
import com.example.demo.response.CustomResponse;
import com.example.demo.service.ContractTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contract-type")
public class ContractTypeController {

    private final ContractTypeService contractTypeService;

    public ContractTypeController(ContractTypeService contractTypeService) {
        this.contractTypeService = contractTypeService;
    }

    @GetMapping("")
    public ResponseEntity<?> getContractTypes() {
        List<ContractTypeDto> contractTypes = contractTypeService.getContractTypes();
        CustomResponse<List<ContractTypeDto>> response = new CustomResponse<>(200, contractTypes);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContractTypeById(@PathVariable Long id) {
        ContractTypeDto contractType = contractTypeService.getContractTypeById(id);
        CustomResponse<ContractTypeDto> response = new CustomResponse<>(200, contractType);
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<?> createContractType(@RequestBody ContractTypePayload contractTypePayload) {
        ContractTypeDto contractType = contractTypeService.createContractType(contractTypePayload);
        CustomResponse<ContractTypeDto> response = new CustomResponse<>(200, contractType);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContractType(@PathVariable Long id, @RequestBody ContractTypePayload contractTypePayload) {
        try {
            contractTypePayload.setId(id);
            ContractTypeDto contractType = contractTypeService.updateContractType(contractTypePayload);
            CustomResponse<ContractTypeDto> response = new CustomResponse<>(200, contractType);
            return ResponseEntity.ok(response);
        } catch (DoesNotExistException e) {
            CustomResponse<String> errorResponse = new CustomResponse<>(404, "Error updating contract type");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContractType(@PathVariable Long id) {
        try {
            ContractTypeDto contractType = contractTypeService.deleteContractType(id);
            CustomResponse<ContractTypeDto> response = new CustomResponse<>(200, contractType);
            return ResponseEntity.ok(response);
        } catch (DoesNotExistException e) {
            CustomResponse<String> errorResponse = new CustomResponse<>(404, "Error deleting contract type");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
