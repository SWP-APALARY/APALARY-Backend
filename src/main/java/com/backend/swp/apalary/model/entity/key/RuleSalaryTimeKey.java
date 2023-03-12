package com.backend.swp.apalary.model.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class RuleSalaryTimeKey implements Serializable {
    @Column(name = "salary_id")
    private Integer salaryId;
    @Column(name = "rule_number")
    private Integer ruleNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleSalaryTimeKey that = (RuleSalaryTimeKey) o;
        return Objects.equals(salaryId, that.salaryId) && Objects.equals(ruleNumber, that.ruleNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salaryId, ruleNumber);
    }
}
