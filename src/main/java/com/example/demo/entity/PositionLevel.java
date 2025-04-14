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
@Table(name = "position_level")
public class PositionLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String positionLevelName;
    private Float baseSalary;
    @OneToMany(mappedBy = "positionLevel")
    private List<User> user;
    @CreationTimestamp
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    public PositionLevel(String positionLevelName, Float baseSalary) {
        this.positionLevelName = positionLevelName;
        this.baseSalary = baseSalary;
    }
}
