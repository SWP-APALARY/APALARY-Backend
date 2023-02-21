package com.backend.swp.apalary.service;

import com.backend.swp.apalary.model.dto.ContractDTO;
import com.backend.swp.apalary.model.dto.RuleSalaryDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContractService {
    ResponseEntity<Void> createContract(ContractDTO contractDTO);
    ResponseEntity<List<ContractDTO>> getAllContract();
    ResponseEntity<ContractDTO> getContractById(String id);
    ResponseEntity<Void> deleteContract(String id);
    ResponseEntity<ContractDTO> getContractByUserId(String userId);
    ResponseEntity<List<RuleSalaryDTO>> getAllRuleSalary();
}
