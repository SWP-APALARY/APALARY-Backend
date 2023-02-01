package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, String> {
}
