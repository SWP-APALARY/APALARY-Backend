package com.backend.swp.apalary.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "rule_salary")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RuleSalary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_number")
    private Integer ruleNumber;
    @Column
    private String description;
    @Column
    private int point;
    @ManyToMany(mappedBy = "ruleSalaries", fetch = FetchType.LAZY)
    private List<Contract> contracts;

}
