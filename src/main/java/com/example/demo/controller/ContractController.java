package com.example.demo.controller;

import com.example.demo.dto.ContractDto;
import com.example.demo.entity.Contract;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.payload.ContractPayload;
import com.example.demo.response.CustomResponse;
import com.example.demo.service.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contract")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllContracts() {
        List<ContractDto> contracts = contractService.getAllContracts();
        CustomResponse<List<ContractDto>> response = new CustomResponse<>(200, contracts);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContractById(@PathVariable Long id) {
        ContractDto contract = contractService.getContractById(id);
        CustomResponse<ContractDto> response = new CustomResponse<>(200, contract);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("")
    public ResponseEntity<?> createContract(@RequestBody ContractPayload contractPayload) {
        ContractDto contract = contractService.createContract(contractPayload);
        CustomResponse<ContractDto> response = new CustomResponse<>(200, contract);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContract(@PathVariable Long id, @RequestBody ContractPayload contractPayload) {
        try {
            contractPayload.setId(id);
            ContractDto contract = contractService.updateContract(contractPayload);
            CustomResponse<ContractDto> response = new CustomResponse<>(200, contract);
            return ResponseEntity.status(201).body(response);
        } catch (DoesNotExistException e) {
            CustomResponse<String> response = new CustomResponse<>(404, "Contract not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContract(@PathVariable Long id) {
        try {
            ContractDto contract = contractService.deleteContract(id);
            CustomResponse<ContractDto> response = new CustomResponse<>(200, contract);
            return ResponseEntity.status(201).body(response);
        } catch (DoesNotExistException e) {
            CustomResponse<String> response = new CustomResponse<>(404, "Contract not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
