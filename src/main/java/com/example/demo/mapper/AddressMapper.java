package com.example.demo.mapper;

import com.example.demo.dto.AddressDto;
import com.example.demo.entity.Address;
import com.example.demo.entity.UserData;
import com.example.demo.payload.AddressPayload;

public class AddressMapper {
    public static Address toAddress(AddressPayload addressPayload, UserData userData) {
        return Address.builder()
                .userData(userData)
                .houseNumber(addressPayload.getHouseNumber())
                .city(addressPayload.getCity())
                .street(addressPayload.getStreet())
                .district(addressPayload.getDistrict())
                .build();
    }

    public static void mapPayloadToAddress(AddressPayload addressPayload, Address address) {
        if(addressPayload.getHouseNumber() != null) address.setHouseNumber(addressPayload.getHouseNumber());
        if(addressPayload.getCity() != null) address.setCity(addressPayload.getCity());
        if(addressPayload.getDistrict() != null) address.setDistrict(addressPayload.getDistrict());
        if(addressPayload.getStreet() != null) address.setStreet(addressPayload.getStreet());
    }

    public static AddressDto toAddressDto(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .userDataId(address.getUserData() != null ? address.getUserData().getId() : null)
                .houseNumber(address.getHouseNumber())
                .street(address.getStreet())
                .city(address.getCity())
                .district(address.getDistrict())
                .createdAt(address.getCreatedAt())
                .updatedAt(address.getUpdatedAt())
                .build();
    }
}
