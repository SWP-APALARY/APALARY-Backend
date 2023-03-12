package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.constant.Status;
import com.backend.swp.apalary.model.entity.Contract;
import com.backend.swp.apalary.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee findEmployeeByIdAndStatus(String id, Status status);
    Employee findEmployeeByUsernameAndStatus(String username, Status status);
    List<Employee> findEmployeeByStatus(Status status);
    Employee findEmployeeByContract(Contract contract);
    boolean existsById(String id);
}
