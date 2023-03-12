package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.model.dto.SalaryDTO;
import com.backend.swp.apalary.service.SalaryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary")
@RequiredArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;
    @Operation(summary = "Get salary by month and year")
    @GetMapping("/month-and-year")
    public ResponseEntity<List<SalaryDTO>> getSalaryByMonthAndYear(@RequestParam int month, @RequestParam int year) {
        return salaryService.getSalaryByMonthAndYear(month, year);
    }
    @Operation(summary = "Get salary of current user")
    @GetMapping("/self")
    public ResponseEntity<List<SalaryDTO>> getSalaryOfCurrentEmployee(@RequestAttribute(required = false) String userId) {
        return salaryService.getSalaryByEmployeeId(userId);
    }
    @Operation(summary = "Get all salary of an employee")
    @GetMapping("/{employeeId}")
    public ResponseEntity<List<SalaryDTO>> getSalaryByEmployeeId(@PathVariable String employeeId) {
        return salaryService.getSalaryByEmployeeId(employeeId);
    }
    @Operation(summary = "Get salary of an employee in a specific month")
    @GetMapping("/employee")
    public ResponseEntity<SalaryDTO> getSalaryOfAnEmployeeWithSpecificMonth(@RequestParam String employeeId, @RequestParam Integer month, @RequestParam Integer year) {
        return salaryService.getSalaryOfAnEmployeeByMonthAndYear(employeeId, month, year);
    }

    @Operation(summary = "Get salary of current employee in a specific month")
    @GetMapping("/self/month-and-year")
    public ResponseEntity<SalaryDTO> getSalaryOfCurrentEmployeeWithSpecificMonth(@RequestAttribute(required = false) String userId, @RequestParam Integer month, @RequestParam Integer year) {
        return salaryService.getSalaryOfAnEmployeeByMonthAndYear(userId, month, year);
    }
}
