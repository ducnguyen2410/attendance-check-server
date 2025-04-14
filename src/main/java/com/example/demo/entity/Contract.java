package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "contract")
public class Contract {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String contractName;
    @ManyToOne
    @JoinColumn(name = "contract_type_id")
    private ContractType contractType;
    @OneToOne(mappedBy = "contract")
    private User user;
    private LocalTime startHour;
    private LocalTime endHour;
    @CreationTimestamp
    private LocalDateTime createTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;

    public Contract(String contractName, ContractType contractType, LocalTime endHour, LocalTime startHour) {
        this.contractName = contractName;
        this.contractType = contractType;
        this.endHour = endHour;
        this.startHour = startHour;
    }
}
