package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.model.dto.DepartmentDTO;
import com.backend.swp.apalary.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;
    @Operation(summary = "Get all department")
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartment() {
        return departmentService.getAllDepartment();
    }
    @Operation(summary = "Get department by name")
    @GetMapping("/{name}")
    public ResponseEntity<DepartmentDTO> getDepartmentByName(@PathVariable String name) {
        return departmentService.getDepartmentByName(name);
    }
}
