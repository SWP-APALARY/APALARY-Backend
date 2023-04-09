package com.backend.swp.apalary.model.entity;

import com.backend.swp.apalary.model.entity.key.RuleSalaryTimeKey;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rule_salary_time")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RuleSalaryTime {
    @EmbeddedId
    private RuleSalaryTimeKey id = new RuleSalaryTimeKey();
    @ManyToOne
    @MapsId("salaryId")
    @JoinColumn(name = "salary_id")
    private Salary salary;
    @ManyToOne
    @MapsId("ruleNumber")
    @JoinColumn(name = "rule_number")
    private RuleSalary ruleSalary;

    @Column(name = "obtained_time")
    private Integer time;
    @Column
    private Integer money;
}
