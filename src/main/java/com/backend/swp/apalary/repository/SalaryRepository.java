package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.entity.Employee;
import com.backend.swp.apalary.model.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {
    List<Salary> findSalaryByMonthAndYear(Integer month, Integer year);
    List<Salary> findSalaryByEmployee(Employee employee);
    Salary findSalaryByEmployeeIdAndMonthAndYear(String employeeId, Integer month, Integer year);
    Salary findSalaryById(Integer id);
    @Query(nativeQuery = true, value = "SELECT sum(net) FROM salary\n" +
            "WHERE month = ?1 AND year = ?2")
    Long sumNetInMonthAndYear(Integer month, Integer year);
    @Query(nativeQuery = true, value = "SELECT sum(bonus) FROM salary\n" +
            "WHERE month = ?1 AND year = ?2")
    Long sumBonusInMonthAndYear(Integer month, Integer year);
    @Query(nativeQuery = true, value = "SELECT sum(penalty) FROM salary\n" +
            "WHERE month = ?1 AND year = ?2")
    Long sumPenaltyInMonthAndYear(Integer month, Integer year);
    boolean existsByEmployeeId(String employeeId);
}
