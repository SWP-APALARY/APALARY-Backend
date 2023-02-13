package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, String> {
    Resident findResidentByUsername(String username);
}
