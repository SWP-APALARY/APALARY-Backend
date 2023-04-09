package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.constant.Status;
import com.backend.swp.apalary.model.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Application findApplicationByIdAndStatus(Integer id, Status status);
    Application findApplicationById(Integer id);
    List<Application> findApplicationByStatus(Status status);
    List<Application> findApplicationByStatusAndApplicationTypeId(Status status, Integer applicationTypeId);
    List<Application> findApplicationByEmployeeId(String employeeId);
    @Query(nativeQuery = true,value = "SELECT *\n" +
            "FROM (SELECT * FROM application \n" +
            "            WHERE YEAR(created_time) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH)\n" +
            "            AND MONTH(created_time) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)\n" +
            "            AND application_type_id = 4) a1\n" +
            "where created_time in (\n" +
            "\tSELECT max(created_time) \n" +
            "    FROM application a2\n" +
            "    where a1.to_employee_id = a2.to_employee_id\n" +
            "    )")
    List<Application> findApplicationPreviousMonth();
}
