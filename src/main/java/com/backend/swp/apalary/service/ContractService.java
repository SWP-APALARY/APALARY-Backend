package com.backend.swp.apalary.service;

import com.backend.swp.apalary.model.dto.ContractDTO;
import com.backend.swp.apalary.model.dto.RuleSalaryDTO;
import com.backend.swp.apalary.model.entity.Contract;
import com.backend.swp.apalary.model.entity.RuleSalary;
import com.backend.swp.apalary.repository.ContractRepository;
import com.backend.swp.apalary.repository.ContractTypeRepository;
import com.backend.swp.apalary.repository.RuleSalaryRepository;
import com.backend.swp.apalary.service.constant.ServiceMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContractService {
    private final ContractRepository contractRepository;
    private final ContractTypeRepository contractTypeRepository;
    private final RuleSalaryRepository ruleSalaryRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LogManager.getLogger(ContractService.class);
    private static final String CREATE_CONTRACT_MESSAGE = "Create contract: ";
    private static final String GET_CONTRACT_MESSAGE = "Get contract: ";
    private static final String DELETE_CONTRACT_MESSAGE = "Delete contract: ";

    public ContractService(ContractRepository contractRepository, ContractTypeRepository contractTypeRepository, RuleSalaryRepository ruleSalaryRepository,@Qualifier("contract") ModelMapper modelMapper) {
        this.contractRepository = contractRepository;
        this.contractTypeRepository = contractTypeRepository;
        this.ruleSalaryRepository = ruleSalaryRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public ResponseEntity<Void> createContract(ContractDTO contractDTO) {
        logger.info("{}{}", CREATE_CONTRACT_MESSAGE, contractDTO);
        if (contractDTO == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Contract contract = modelMapper.map(contractDTO, Contract.class);
        for (int ruleNumber: contractDTO.getRuleSalaryRuleNumbers()) {
            RuleSalary ruleSalary = ruleSalaryRepository.findRuleSalaryByRuleNumber(ruleNumber);
            if (ruleSalary == null) {
                logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE.getMessage());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            contract.getRuleSalaries().add(ruleSalary);
            ruleSalary.getContracts().add(contract);
        }
        contractRepository.save(contract);
        logger.info("Create Contract successfully.");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<List<RuleSalaryDTO>> getAllRuleSalary() {
        logger.info("{}", "Get all rules salary: ");
        List<RuleSalaryDTO> ruleSalaries = ruleSalaryRepository.findAll().stream().map(ruleSalary -> modelMapper.map(ruleSalary, RuleSalaryDTO.class)).toList();

        logger.info("Get all rule salaries successfully.");
        return new ResponseEntity<>(ruleSalaries, HttpStatus.OK);
    }

}
