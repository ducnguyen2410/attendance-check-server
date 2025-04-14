package com.example.demo.service;

import com.example.demo.dto.AddressDto;
import com.example.demo.entity.Address;
import com.example.demo.payload.AddressPayload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {
    public abstract List<AddressDto> getAddresses();
    public abstract AddressDto getAddressById(Long id);
    public abstract AddressDto createAddress(AddressPayload address);
    public abstract AddressDto updateAddress(AddressPayload address);
    public abstract AddressDto deleteAddress(Long id);
}
