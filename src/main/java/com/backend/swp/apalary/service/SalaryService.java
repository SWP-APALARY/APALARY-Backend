package com.backend.swp.apalary.service;

import com.backend.swp.apalary.model.dto.EmployeeDTO;
import com.backend.swp.apalary.model.dto.SalaryDTO;
import com.backend.swp.apalary.model.response.SalaryDetailMonthly;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SalaryService {
    ResponseEntity<List<SalaryDTO>> getSalaryByMonthAndYear(Integer month, Integer year);

    ResponseEntity<List<SalaryDTO>> getSalaryByEmployeeId(String employeeId);

    ResponseEntity<SalaryDTO> getSalaryOfAnEmployeeByMonthAndYear(String employeeId, int month, int year);

    ResponseEntity<SalaryDTO> getSalaryOfAnEmployeeBySalaryId(Integer id);


    //cron = "0 0 3 2 * ?", zone = "Asia/Ho_Chi_Minh"
    void systemCalculateSalaryMonthly();

    ResponseEntity<Void> calculateSalaryManually();

    ResponseEntity<List<EmployeeDTO>> getEmployeeNotHaveSalaryInPreviousMonth();

    ResponseEntity<List<SalaryDetailMonthly>> getSalaryDetailsOfAYear(int year);
}
