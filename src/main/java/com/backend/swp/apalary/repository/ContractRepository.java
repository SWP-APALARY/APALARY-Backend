package com.backend.swp.apalary.repository;

import com.backend.swp.apalary.model.constant.Status;
import com.backend.swp.apalary.model.entity.Contract;
import com.backend.swp.apalary.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {
    Contract findContractByIdAndStatus(String id, Status status);
    List<Contract> findContractByStatus(Status status);
    Contract findContractByEmployeeAndStatus(Employee employee, Status status);
    @Query(nativeQuery = true, value = "SELECT * FROM contract\n" +
            "WHERE NOT id IN (SELECT contract_id FROM employee) AND status = 'ACTIVE'")
    List<Contract> findContractNotAssigned();
}
