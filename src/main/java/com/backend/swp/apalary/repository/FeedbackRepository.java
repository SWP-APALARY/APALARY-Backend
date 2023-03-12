package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    @Query(nativeQuery = true, value = "SELECT avg(star) FROM feedback\n" +
            "WHERE YEAR(created_date) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH)\n" +
            "AND MONTH(created_date) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)\n" +
            "AND employee_id = ?1")
    Double averageStarOfAnEmployee(String employeeId);
    @Query(nativeQuery = true, value = "SELECT count(*) FROM feedback\n" +
            "WHERE YEAR(created_date) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH)\n" +
            "AND MONTH(created_date) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)\n" +
            "AND employee_id = ?1")
    Integer countFeedbackOfAnEmployee(String employeeId);
    @Query(nativeQuery = true, value = "SELECT count(star) FROM feedback\n" +
            "WHERE YEAR(created_date) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH)\n" +
            "AND employee_id = ?1")
    Integer countFeedbackOfAnEmployeeOfEntireYear(String employeeId);
}
