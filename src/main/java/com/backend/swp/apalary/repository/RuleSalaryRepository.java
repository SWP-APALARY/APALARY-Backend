package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.entity.RuleSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleSalaryRepository extends JpaRepository<RuleSalary, Integer> {
    RuleSalary findRuleSalaryByRuleNumber(Integer ruleNumber);
}
