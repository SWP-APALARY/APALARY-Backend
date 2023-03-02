package com.backend.swp.apalary.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "salary")
@AllArgsConstructor
@NoArgsConstructor
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    private Integer month;
    @Column
    private Integer year;
    @Column
    private Integer net;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;
}
