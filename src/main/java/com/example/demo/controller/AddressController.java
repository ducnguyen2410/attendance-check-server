package com.example.demo.controller;

import com.example.demo.dto.AddressDto;
import com.example.demo.entity.Address;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.payload.AddressPayload;
import com.example.demo.response.CustomResponse;
import com.example.demo.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllAddresses() {
        List<AddressDto> addresses = addressService.getAddresses();
        CustomResponse<List<AddressDto>> response = new CustomResponse<>(200, addresses);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable Long id) {
        AddressDto address = addressService.getAddressById(id);
        CustomResponse<AddressDto> response = new CustomResponse<>(200, address);
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<?> createAddress(@RequestBody AddressPayload addressPayload) {
        AddressDto address = addressService.createAddress(addressPayload);
        CustomResponse<AddressDto> response = new CustomResponse<>(200, address);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddress(@PathVariable Long id, @RequestBody AddressPayload addressPayload) {
        try {
            addressPayload.setId(id);
            AddressDto updatedAddress = addressService.updateAddress(addressPayload);
            CustomResponse<AddressDto> response = new CustomResponse<>(200, updatedAddress);
            return ResponseEntity.status(200).body(response);
        } catch (DoesNotExistException e) {
            CustomResponse<String> response = new CustomResponse<>(404, "Address not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id) {
        try {
            AddressDto deletedAddress = addressService.deleteAddress(id);
            CustomResponse<AddressDto> response = new CustomResponse<>(200, deletedAddress);
            return ResponseEntity.status(200).body(response);
        } catch (DoesNotExistException e) {
            CustomResponse<String> response = new CustomResponse<>(404, "Address not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
