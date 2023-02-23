package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.model.dto.EmployeeDTO;
import com.backend.swp.apalary.service.EmployeeService;
import com.backend.swp.apalary.service.Impl.EmployeeServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    final
    EmployeeService employeeService;

    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee() {
        return employeeService.getAllEmployee();
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable String id) {
        return employeeService.getEmployeeById(id);
    }
    @GetMapping
    public ResponseEntity<EmployeeDTO> getEmployee(@RequestAttribute(required = false) String userId) {
        return employeeService.getEmployeeById(userId);
    }
    @PutMapping
    public ResponseEntity<Void> updateProfile(@RequestBody EmployeeDTO employeeDTO, @RequestAttribute(required = false) String userId) {
        return employeeService.updateEmployee(employeeDTO, userId);
    }
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> banEmployee(@PathVariable String employeeId) {
        return employeeService.banEmployee(employeeId);
    }
}
