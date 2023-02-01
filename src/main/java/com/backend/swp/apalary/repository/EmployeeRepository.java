package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
