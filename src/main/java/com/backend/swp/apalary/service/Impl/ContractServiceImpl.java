package com.backend.swp.apalary.service.Impl;

import com.backend.swp.apalary.model.constant.Status;
import com.backend.swp.apalary.model.dto.ContractDTO;
import com.backend.swp.apalary.model.dto.RuleSalaryDTO;
import com.backend.swp.apalary.model.entity.Contract;
import com.backend.swp.apalary.model.entity.ContractType;
import com.backend.swp.apalary.model.entity.Employee;
import com.backend.swp.apalary.model.entity.RuleSalary;
import com.backend.swp.apalary.model.response.ContractResponseInList;
import com.backend.swp.apalary.repository.ContractRepository;
import com.backend.swp.apalary.repository.ContractTypeRepository;
import com.backend.swp.apalary.repository.EmployeeRepository;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class ContractServiceImpl implements com.backend.swp.apalary.service.ContractService {
    private final ContractRepository contractRepository;
    private final EmployeeRepository employeeRepository;
    private final ContractTypeRepository contractTypeRepository;
    private final RuleSalaryRepository ruleSalaryRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LogManager.getLogger(ContractServiceImpl.class);
    private static final String CREATE_CONTRACT_MESSAGE = "Create contract: ";
    private static final String GET_CONTRACT_MESSAGE = "Get contract: ";
    private static final String DELETE_CONTRACT_MESSAGE = "Delete contract: ";

    public ContractServiceImpl(ContractRepository contractRepository, EmployeeRepository employeeRepository, ContractTypeRepository contractTypeRepository, RuleSalaryRepository ruleSalaryRepository,@Qualifier("contractMapper") ModelMapper modelMapper) {
        this.contractRepository = contractRepository;
        this.employeeRepository = employeeRepository;
        this.contractTypeRepository = contractTypeRepository;
        this.ruleSalaryRepository = ruleSalaryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public ResponseEntity<Void> createContract(ContractDTO contractDTO) {
        logger.info("{}{}", CREATE_CONTRACT_MESSAGE, contractDTO);
        if (contractDTO == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ContractType contractType = contractTypeRepository.findContractTypeById(contractDTO.getContractTypeId());
        if (contractType == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Contract contract = modelMapper.map(contractDTO, Contract.class);
        int base = contract.getBase();
        int tax;
        if (base <= 5000000) {
            tax = 5;
        }
        else if (base <= 10000000) {
            tax = 10;
        }
        else if (base <= 18000000) {
            tax = 15;
        }
        else if (base <= 32000000) {
            tax = 20;
        } else if (base <= 52000000) {
            tax = 25;
        } else if (base <= 80000000) {
            tax = 30;
        } else {
            tax = 35;
        }
        contract.setTax(tax);
        contract.setAssurances(8 + 1.5 + 1);
        contract.setStatus(Status.ACTIVE);
        contract.setContractType(contractType);
        contract.setRuleSalaries(new ArrayList<>());
        for (int ruleNumber : contractDTO.getRuleSalaryRuleNumber()) {
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

    @Override
    @Transactional
    public ResponseEntity<Void> resignContract(ContractDTO contractDTO, String employeeId) {
        logger.info("Resign contract {} for employee id {}", contractDTO, employeeId);
        if (employeeId == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (contractDTO == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Employee employee = employeeRepository.findEmployeeByIdAndStatus(employeeId, Status.ACTIVE);
        if (employee == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ContractType contractType = contractTypeRepository.findContractTypeById(contractDTO.getContractTypeId());
        if (contractType == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Contract contract = modelMapper.map(contractDTO, Contract.class);
        int base = contract.getBase();
        int tax;
        if (base <= 5000000) {
            tax = 5;
        }
        else if (base <= 10000000) {
            tax = 10;
        }
        else if (base <= 18000000) {
            tax = 15;
        }
        else if (base <= 32000000) {
            tax = 20;
        } else if (base <= 52000000) {
            tax = 25;
        } else if (base <= 80000000) {
            tax = 30;
        } else {
            tax = 35;
        }
        contract.setTax(tax);
        contract.setAssurances(8 + 1.5 + 1);
        contract.setStatus(Status.ACTIVE);
        contract.setContractType(contractType);
        contract.setRuleSalaries(new ArrayList<>());
        for (int ruleNumber : contractDTO.getRuleSalaryRuleNumber()) {
            RuleSalary ruleSalary = ruleSalaryRepository.findRuleSalaryByRuleNumber(ruleNumber);
            if (ruleSalary == null) {
                logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE.getMessage());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            contract.getRuleSalaries().add(ruleSalary);
            ruleSalary.getContracts().add(contract);
        }
        Contract oldContract = employee.getContract();
        oldContract.setStatus(Status.INACTIVE);
        contractRepository.save(oldContract);
        employee.setContract(contract);
        contractRepository.save(contract);
        employeeRepository.save(employee);
        logger.info("Create Contract successfully.");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Override
    @Transactional
    public ResponseEntity<List<ContractResponseInList>> getAllContract() {
        logger.info("{}{}", GET_CONTRACT_MESSAGE, "all");
        List<Contract> contracts = contractRepository.findContractByStatus(Status.ACTIVE);
        List<ContractResponseInList> contractDTOS = contracts.stream().map(contract -> {
            ContractResponseInList dto = modelMapper.map(contract, ContractResponseInList.class);
            dto.setSocialAssurances(8.0);
            dto.setMedicalAssurances(1.5);
            dto.setAccidentalAssurances(1.0);
            return dto;
        }).toList();
        logger.info("Get all contracts successfully.");
        return new ResponseEntity<>(contractDTOS, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<ContractDTO> getContractById(String id) {
        logger.info("{}{}", GET_CONTRACT_MESSAGE, id);
        if (id == null) {
            logger.info(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Contract contract = contractRepository.findContractByIdAndStatus(id, Status.ACTIVE);
        if (contract == null) {
            logger.info(ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ContractDTO contractDTO = modelMapper.map(contract, ContractDTO.class);
        contractDTO.setSocialAssurance(8.0);
        contractDTO.setMedicalAssurance(1.5);
        contractDTO.setAccidentalAssurance(1.0);
        logger.info("Get contract by id {} successfully.", id);
        return new ResponseEntity<>(contractDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ContractResponseInList>> getContractNotAssigned() {
        logger.info("{}{}", GET_CONTRACT_MESSAGE, "all contracts are not assigned");
        List<Contract> contracts = contractRepository.findContractNotAssigned();
        List<ContractResponseInList> contractDTOS = contracts.stream().map(contract -> {
            ContractResponseInList dto = modelMapper.map(contract, ContractResponseInList.class);
            dto.setSocialAssurances(8.0);
            dto.setMedicalAssurances(1.5);
            dto.setAccidentalAssurances(1.0);
            return dto;
        }).toList();
        logger.info("Get all contracts are not assigned successfully.");
        return new ResponseEntity<>(contractDTOS, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<List<ContractResponseInList>> getInactiveContract() {
        logger.info("{}{}", GET_CONTRACT_MESSAGE, "inactive contracts");
        List<Contract> contracts = contractRepository.findContractByStatus(Status.INACTIVE);
        List<ContractResponseInList> contractDTOS = contracts.stream().map(contract -> {
            ContractResponseInList dto = modelMapper.map(contract, ContractResponseInList.class);
            dto.setSocialAssurances(8.0);
            dto.setMedicalAssurances(1.5);
            dto.setAccidentalAssurances(1.0);
            return dto;
        }).toList();
        logger.info("Get all inactive contracts successfully.");
        return new ResponseEntity<>(contractDTOS, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Void> deleteContract(String id) {
        logger.info("{}{}", DELETE_CONTRACT_MESSAGE, id);
        if (id == null) {
            logger.info(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Contract contract = contractRepository.findContractByIdAndStatus(id, Status.ACTIVE);
        if (contract == null) {
            logger.info(ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        contract.setStatus(Status.INACTIVE);
        contractRepository.save(contract);
        logger.info("Delete contract id {} successfully.", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ContractDTO> getContractByUserId(String userId) {
        logger.info("{}{}", GET_CONTRACT_MESSAGE, userId);
        if (userId == null) {
            logger.info(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Employee employee = employeeRepository.findEmployeeByIdAndStatus(userId, Status.ACTIVE);
        if (employee == null) {
            logger.info(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Contract contract = contractRepository.findContractByEmployeeAndStatus(employee, Status.ACTIVE);
        ContractDTO dto = modelMapper.map(contract, ContractDTO.class);
        dto.setSocialAssurance(8.0);
        dto.setMedicalAssurance(1.5);
        dto.setAccidentalAssurance(1.0);
        logger.info("Get contract of user {} successfully.", userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @Override
    @Transactional
    public ResponseEntity<List<RuleSalaryDTO>> getAllRuleSalary() {
        logger.info("{}", "Get all rules salary: ");
        List<RuleSalaryDTO> ruleSalaries = ruleSalaryRepository.findAll().stream().map(ruleSalary -> modelMapper.map(ruleSalary, RuleSalaryDTO.class)).toList();

        logger.info("Get all rule salaries successfully.");
        return new ResponseEntity<>(ruleSalaries, HttpStatus.OK);
    }



}
