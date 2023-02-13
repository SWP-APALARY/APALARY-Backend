package com.backend.swp.apalary.service;

import com.backend.swp.apalary.config.JwtService;
import com.backend.swp.apalary.model.constant.Status;
import com.backend.swp.apalary.model.entity.Contract;
import com.backend.swp.apalary.model.entity.Employee;
import com.backend.swp.apalary.model.entity.Resident;
import com.backend.swp.apalary.model.request.CreateEmployeeRequest;
import com.backend.swp.apalary.model.request.CreateResidentRequest;
import com.backend.swp.apalary.model.request.LoginRequest;
import com.backend.swp.apalary.repository.*;
import com.backend.swp.apalary.service.constant.ServiceMessage;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final EmployeeRepository employeeRepository;
    private final ResidentRepository residentRepository;
    private final ContractRepository contractRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final Logger logger = LogManager.getLogger(AuthService.class);
    private static final String CREATE_EMPLOYEE_MESSAGE = "Create employee: ";
    private static final String CREATE_RESIDENT_MESSAGE = "Create resident: ";
    public ResponseEntity<String> createEmployee(CreateEmployeeRequest employeeDTO) {
        logger.info("{}{}",CREATE_EMPLOYEE_MESSAGE, employeeDTO.getUsername());
        Contract contract = contractRepository.findContractById(employeeDTO.getContractId());
        if (contract == null) {
            logger.warn(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        employee.setStatus(Status.ACTIVE);
        employeeRepository.save(employee);
        String jwt = jwtService.generateToken(employee);
        logger.info("Create employee id {} successfully.", employeeDTO.getId());
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    public ResponseEntity<String> createResident(CreateResidentRequest residentDTO) {
        logger.info("{}{}",CREATE_RESIDENT_MESSAGE, residentDTO.getUsername());
        Resident resident = modelMapper.map(residentDTO, Resident.class);
        resident.setPassword(passwordEncoder.encode(residentDTO.getPassword()));
        resident.setStatus(Status.ACTIVE);
        residentRepository.save(resident);
        String jwt = jwtService.generateToken(resident);
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }

    public ResponseEntity<String> login(LoginRequest userLogin) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword())
        );
        Employee employee = employeeRepository.findEmployeeByUsername(userLogin.getUsername());
        UserDetails userDetails = employee != null ? employee : residentRepository.findResidentByUsername(userLogin.getUsername());
        if (userDetails == null) {
            logger.warn(ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        String jwt = jwtService.generateToken(userDetails);
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }
}
