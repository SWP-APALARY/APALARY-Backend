package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.entity.Feedback;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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
    @Query(nativeQuery = true, value = "SELECT * FROM feedback\n" +
            "WHERE month(created_date) = ?1 AND year(created_date) = ?2")
    List<Feedback> findFeedbackByMonthAndYear(Integer month, Integer year);

    @Query(nativeQuery = true, value = "SELECT * FROM feedback\n" +
            "WHERE employee_id = ?1 AND month(created_date) = ?2 AND year(created_date) = ?3")
    List<Feedback> findFeedbackOfAnEmployeeByMonthAndYear(String employeeId, Integer month, Integer year);
    List<Feedback> findFeedbackByEmployeeId(String employeeId);
}
