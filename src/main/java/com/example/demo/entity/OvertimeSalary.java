package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "overtime_salary")
public class OvertimeSalary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String overtimeType;
    private Float overtimeCharge;
    @OneToMany(mappedBy = "overtimeSalary")
    private List<Overtime> overtime;
    @CreationTimestamp
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    public OvertimeSalary(String overtimeType, Float overtimeCharge) {
        this.overtimeType = overtimeType;
        this.overtimeCharge = overtimeCharge;
    }
}
