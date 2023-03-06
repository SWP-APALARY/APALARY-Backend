package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.model.dto.ContractDTO;
import com.backend.swp.apalary.model.dto.RuleSalaryDTO;
import com.backend.swp.apalary.model.response.ContractResponseInList;
import com.backend.swp.apalary.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contract")
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;
    @Operation(summary = "Create contract by providing dto")
    @PostMapping
    public ResponseEntity<Void> createContract(@RequestBody ContractDTO contractDTO) {
        return contractService.createContract(contractDTO);
    }
    @Operation(summary = "Resign contract")
    @PostMapping("/resign")
    public ResponseEntity<Void> resignContract(@RequestBody ContractDTO contractDTO, @RequestParam String employeeId) {
        return contractService.resignContract(contractDTO, employeeId);
    }
    @Operation(summary = "Get all contract")
    @GetMapping("/all")
    public ResponseEntity<List<ContractResponseInList>> getAllContract() {
        return contractService.getAllContract();
    }
    @Operation(summary = "Get contract by id")
    @GetMapping("/{id}")
    public ResponseEntity<ContractDTO> getContractById(@PathVariable String id) {
        return contractService.getContractById(id);
    }
    @Operation(summary = "Get current user's contract")
    @GetMapping
    public ResponseEntity<ContractDTO> getUserContract(@RequestAttribute(required = false) String userId) {
        return contractService.getContractByUserId(userId);
    }
    @Operation(summary = "Get all rule salaries")
    @GetMapping("rules-salary")
    public ResponseEntity<List<RuleSalaryDTO>> getAllRuleSalary() {
        return contractService.getAllRuleSalary();
    }
    @Operation(summary = "Delete contract by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable String id) {
        return contractService.deleteContract(id);
    }
}
