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
@Builder
@Table(name = "overtime")
public class Overtime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "overtime_salary_id")
    private OvertimeSalary overtimeSalary;
    @CreationTimestamp
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updatedTime;
    private Float duration;

    public Overtime(Float duration, User user, OvertimeSalary overtimeSalary) {
        this.duration = duration;
        this.user = user;
        this.overtimeSalary = overtimeSalary;
    }
}
