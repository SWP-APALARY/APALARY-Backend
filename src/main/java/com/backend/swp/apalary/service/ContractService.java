package com.backend.swp.apalary.service;

import com.backend.swp.apalary.model.dto.ContractDTO;
import com.backend.swp.apalary.model.dto.RuleSalaryDTO;
import com.backend.swp.apalary.model.response.ContractResponseInList;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContractService {
    ResponseEntity<Void> createContract(ContractDTO contractDTO);
    ResponseEntity<Void> resignContract(ContractDTO contractDTO, String employeeId);
    ResponseEntity<List<ContractResponseInList>> getAllContract();
    ResponseEntity<ContractDTO> getContractById(String id);

    ResponseEntity<List<ContractResponseInList>> getContractNotAssigned();

    ResponseEntity<List<ContractResponseInList>> getInactiveContract();

    ResponseEntity<Void> deleteContract(String id);
    ResponseEntity<ContractDTO> getContractByUserId(String userId);
    ResponseEntity<List<RuleSalaryDTO>> getAllRuleSalary();
}
