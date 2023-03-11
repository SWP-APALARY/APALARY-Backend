package com.backend.swp.apalary.service;

import com.backend.swp.apalary.model.dto.SalaryDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SalaryService {
    ResponseEntity<List<SalaryDTO>> getSalaryByMonthAndYear(Integer month, Integer year);

    ResponseEntity<List<SalaryDTO>> getSalaryByEmployeeId(String employeeId);

    ResponseEntity<SalaryDTO> getSalaryOfAnEmployeeByMonthAndYear(String employeeId, int month, int year);

    void calculateSalary();
}
