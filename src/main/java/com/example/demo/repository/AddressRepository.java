package com.example.demo.repository;

import com.example.demo.entity.Address;
import com.example.demo.payload.AddressPayload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
