package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.entity.Employee;
import com.backend.swp.apalary.model.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {
    List<Salary> findSalaryByMonthAndYear(Integer month, Integer year);
    List<Salary> findSalaryByEmployee(Employee employee);
    Salary findSalaryByEmployeeIdAndMonthAndYear(String employeeId, Integer month, Integer year);
    boolean existsByEmployeeId(String employeeId);
}
