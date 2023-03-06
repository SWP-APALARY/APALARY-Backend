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
    @Query(nativeQuery = true,value = "SELECT * FROM application\n" +
            "WHERE YEAR(created_time) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH)\n" +
            "AND MONTH(created_time) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)\n" +
            "AND application_type_id = 4")
    List<Application> findApplicationPreviousMonth();
}
