package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.constant.Status;
import com.backend.swp.apalary.model.entity.Contract;
import com.backend.swp.apalary.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee findEmployeeByIdAndStatus(String id, Status status);
    Employee findEmployeeByUsernameAndStatus(String username, Status status);
    Employee findEmployeeById(String id);
    List<Employee> findEmployeeByStatus(Status status);
    Employee findEmployeeByContract(Contract contract);
    @Query(nativeQuery = true, value = "select max(id) from employee where id like ?1")
    String lastEmployeeBeginWith(String begin);
    @Query(nativeQuery = true, value = "select * from employee \n" +
            "where status = 'ACTIVE' AND id not in (\n" +
            "select distinct employee_id from salary \n" +
            "where month = MONTH(CURRENT_DATE - INTERVAL 1 MONTH) " +
            "and year = YEAR(CURRENT_DATE - INTERVAL 1 MONTH))")
    List<Employee> findEmployeeHaveNoSalaryPreviousMonth();
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByIdentifyNumber(String identifyNumber);
}
