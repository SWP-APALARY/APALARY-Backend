package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
