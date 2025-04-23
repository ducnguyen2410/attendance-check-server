package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String houseNumber;
    private String street;
    private String city;
    private String district;
    @OneToOne(mappedBy = "address")
    private UserData userData;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Address(String houseNumber, String city, String district, String street) {
        this.houseNumber = houseNumber;
        this.city = city;
        this.district = district;
        this.street = street;
    }
}
