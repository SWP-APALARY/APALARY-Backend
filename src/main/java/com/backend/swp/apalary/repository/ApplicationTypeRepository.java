package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.entity.ApplicationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationTypeRepository extends JpaRepository<ApplicationType, Integer> {
    ApplicationType findApplicationTypeById(Integer id);
}
