package com.backend.swp.apalary.service;

import com.backend.swp.apalary.model.dto.EmployeeDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {
    ResponseEntity<EmployeeDTO> getEmployeeById(String id);
    ResponseEntity<List<EmployeeDTO>> getAllEmployee();
    ResponseEntity<Void> updateEmployee(EmployeeDTO employeeDTO, String userId);
}
