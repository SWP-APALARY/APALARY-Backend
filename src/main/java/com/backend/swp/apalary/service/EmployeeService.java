package com.backend.swp.apalary.service;

import com.backend.swp.apalary.config.exception.BadRequestException;
import com.backend.swp.apalary.model.constant.Role;
import com.backend.swp.apalary.model.dto.EmployeeDTO;
import com.backend.swp.apalary.model.response.EmployeeResponseInList;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployeeService {
    ResponseEntity<EmployeeDTO> getEmployeeById(String id);
    ResponseEntity<List<EmployeeDTO>> getAllActiveEmployee(String employeeId, Role role);

    ResponseEntity<List<EmployeeDTO>> getAllInactiveEmployee(String employeeId, Role role);

    ResponseEntity<List<EmployeeDTO>> getAllEmployee(String employeeId, Role role);

    ResponseEntity<Void> updateEmployee(EmployeeDTO employeeDTO, String userId);


    ResponseEntity<Void> removeEmployee(String employeeId, String userId) throws BadRequestException;

    ResponseEntity<Void> recoverEmployee(String employeeId);
}
