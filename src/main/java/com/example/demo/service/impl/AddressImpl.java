package com.example.demo.service.impl;

import com.example.demo.dto.AddressDto;
import com.example.demo.entity.Address;
import com.example.demo.entity.UserData;
import com.example.demo.exception.AlreadyExistsException;
import com.example.demo.exception.DoesNotExistException;
import com.example.demo.exception.IDNotMatchData;
import com.example.demo.mapper.AddressMapper;
import com.example.demo.payload.AddressPayload;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.UserDataRepository;
import com.example.demo.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AddressImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserDataRepository userDataRepository;

    public AddressImpl(AddressRepository addressRepository, UserDataRepository userDataRepository) {
        this.addressRepository = addressRepository;
        this.userDataRepository = userDataRepository;
    }

    @Override
    public List<AddressDto> getAddresses() {
        List<Address> addresses = addressRepository.findAll();
        List<AddressDto> addressDtoList = new ArrayList<>();
        for (Address address : addresses) {
            AddressDto addressDto = AddressMapper.toAddressDto(address);
            addressDtoList.add(addressDto);
        }
        return addressDtoList;
    }

    @Override
    public AddressDto getAddressById(Long id) {
        Address address = addressRepository.findById(id).orElse(null);
        if (address == null) return null;
        return AddressMapper.toAddressDto(address);
    }

    @Override
    public AddressDto createAddress(AddressPayload addressPayload) {
        UserData userData = userDataRepository.findById(addressPayload.getUserDataId()).orElse(null);
        if (userData == null) {
            throw new DoesNotExistException("User data does not exist");
        }
        if(userData.getAddress() != null) {
            throw new AlreadyExistsException("User address already exists");
        }
        Address address = AddressMapper.toAddress(addressPayload, userData);
        userData.setAddress(address);
        address.setUserData(userData);

        addressRepository.save(address);
        return AddressMapper.toAddressDto(address);
    }

    @Override
    public AddressDto updateAddress(AddressPayload addressPayload) {
        Address address = addressRepository.findById(addressPayload.getId()).orElse(null);
        if (address == null) {
            throw new DoesNotExistException("Address does not exist");
        }
        UserData userData = address.getUserData();
        AddressMapper.mapPayloadToAddress(addressPayload, address);
        address.setUserData(userData);
        userData.setAddress(address);
        addressRepository.save(address);
        return AddressMapper.toAddressDto(address);
    }

    @Override
    public AddressDto deleteAddress(Long id) {
        Address address = addressRepository.findById(id).orElse(null);
        if (address == null) {
            throw new DoesNotExistException("Address does not exist");
        }
        UserData userData = address.getUserData();
        addressRepository.delete(address);
        userData.setAddress(null);
        userDataRepository.save(userData);
        return AddressMapper.toAddressDto(address);
    }
}
