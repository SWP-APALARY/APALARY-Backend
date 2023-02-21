package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.model.dto.DepartmentDTO;
import com.backend.swp.apalary.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartment() {
        return departmentService.getAllDepartment();
    }

    @GetMapping("/{name}")
    public ResponseEntity<DepartmentDTO> getDepartmentByName(@PathVariable String name) {
        return departmentService.getDepartmentByName(name);
    }
}
