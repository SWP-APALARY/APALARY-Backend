package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee findEmployeeById(String id);
}
