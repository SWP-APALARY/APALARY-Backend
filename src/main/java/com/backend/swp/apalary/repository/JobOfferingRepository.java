package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.constant.Status;
import com.backend.swp.apalary.model.entity.JobOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobOfferingRepository extends JpaRepository<JobOffering, Integer> {
    List<JobOffering> findJobOfferingByStatus(Status status);
    JobOffering findJobOfferingByIdAndStatus(Integer id, Status status);
}
