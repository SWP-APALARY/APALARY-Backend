package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.model.dto.ContractDTO;
import com.backend.swp.apalary.model.dto.RuleSalaryDTO;
import com.backend.swp.apalary.model.response.ContractResponseInList;
import com.backend.swp.apalary.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contract")
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;
    @PostMapping
    public ResponseEntity<Void> createContract(@RequestBody ContractDTO contractDTO) {
        return contractService.createContract(contractDTO);
    }
    @PostMapping("/resign")
    public ResponseEntity<Void> resignContract(@RequestBody ContractDTO contractDTO, @RequestParam String employeeId) {
        return contractService.resignContract(contractDTO, employeeId);
    }
    @GetMapping("/all")
    public ResponseEntity<List<ContractResponseInList>> getAllContract() {
        return contractService.getAllContract();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ContractDTO> getContractById(@PathVariable String id) {
        return contractService.getContractById(id);
    }
    @GetMapping
    public ResponseEntity<ContractDTO> getUserContract(@RequestAttribute(required = false) String userId) {
        return contractService.getContractByUserId(userId);
    }
    @GetMapping("rules-salary")
    public ResponseEntity<List<RuleSalaryDTO>> getAllRuleSalary() {
        return contractService.getAllRuleSalary();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable String id) {
        return contractService.deleteContract(id);
    }
}
