package com.backend.swp.apalary.service;

import com.backend.swp.apalary.model.dto.DepartmentDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DepartmentService {
    ResponseEntity<List<DepartmentDTO>> getAllDepartment();

    ResponseEntity<DepartmentDTO> getDepartmentByName(String name);
}
