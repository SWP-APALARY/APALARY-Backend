package com.backend.swp.apalary.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "rule_salary")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RuleSalary {
    public RuleSalary(Integer ruleNumber) {
        this.ruleNumber = ruleNumber;
    }
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
    @OneToMany(mappedBy = "ruleSalary", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RuleSalaryTime> times;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleSalary that = (RuleSalary) o;
        return Objects.equals(ruleNumber, that.ruleNumber);
    }
    @Override
    public int hashCode() {
        return Objects.hash(ruleNumber);
    }


}
