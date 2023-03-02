package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.model.dto.SalaryDTO;
import com.backend.swp.apalary.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary")
@RequiredArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;
    @GetMapping("/month-and-year")
    public ResponseEntity<List<SalaryDTO>> getSalaryByMonthAndYear(@RequestParam int month, @RequestParam int year) {
        return salaryService.getSalaryByMonthAndYear(month, year);
    }
    @GetMapping("/self")
    public ResponseEntity<List<SalaryDTO>> getSalaryByEmployeeId(@RequestAttribute(required = false) String userId) {
        return salaryService.getSalaryByEmployeeId(userId);
    }
}
