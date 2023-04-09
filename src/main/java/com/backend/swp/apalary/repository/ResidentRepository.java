package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.constant.Status;
import com.backend.swp.apalary.model.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, String> {
    Resident findResidentByUsernameAndStatus(String username, Status status);
    Resident findResidentByIdAndStatus(String id, Status status);
    List<Resident> findResidentByStatus(Status status);
    @Query(nativeQuery = true, value = "select max(id) from resident")
    String lastResidentId();
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByIdentifyNumber(String identifyNumber);
}
