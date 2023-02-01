package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.entity.JobOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferingRepository extends JpaRepository<JobOffering, Integer> {
}
