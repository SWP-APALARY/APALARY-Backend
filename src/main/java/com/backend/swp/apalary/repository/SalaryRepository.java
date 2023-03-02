package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.entity.Contract;
import com.backend.swp.apalary.model.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {
    List<Salary> findSalaryByMonthAndYear(Integer month, Integer year);
    List<Salary> findSalaryByContract(Contract contract);
}
