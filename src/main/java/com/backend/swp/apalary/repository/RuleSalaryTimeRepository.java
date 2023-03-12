package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.entity.RuleSalaryTime;
import com.backend.swp.apalary.model.entity.key.RuleSalaryTimeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleSalaryTimeRepository extends JpaRepository<RuleSalaryTime, RuleSalaryTimeKey> {
}
