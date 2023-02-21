package com.backend.swp.apalary.service.Impl;

import com.backend.swp.apalary.model.constant.Status;
import com.backend.swp.apalary.model.dto.EmployeeDTO;
import com.backend.swp.apalary.model.entity.Employee;
import com.backend.swp.apalary.repository.EmployeeRepository;
import com.backend.swp.apalary.service.constant.ServiceMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements com.backend.swp.apalary.service.EmployeeService {
    final
    EmployeeRepository employeeRepository;
    final
    ModelMapper modelMapper;
    private static final Logger logger = LogManager.getLogger(EmployeeServiceImpl.class);
    private static final String GET_EMPLOYEE_MESSAGE = "Get employee: ";
    private static final String UPDATE_EMPLOYEE_MESSAGE = "Update employee: ";
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<EmployeeDTO> getEmployeeById(String id) {
        logger.info("{}{}", GET_EMPLOYEE_MESSAGE, id);
        if (id == null) {
            logger.warn("{}" , ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Employee employee = employeeRepository.findEmployeeByIdAndStatus(id, Status.ACTIVE);
        if (employee == null) {
            logger.warn("{}", ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        logger.info("Get employee id {} successfully.", id);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee() {
        logger.info("{}{}", GET_EMPLOYEE_MESSAGE, "all");
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOS = employees.stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class)).toList();
        logger.info("Get all employee successfully.");
        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateEmployee(EmployeeDTO employeeDTO, String userId) {
        logger.info("{}{}", UPDATE_EMPLOYEE_MESSAGE, employeeDTO);
        if (employeeDTO == null) {
            logger.warn("{}" , ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!employeeDTO.getId().equals(userId)) {
            logger.warn("{}" , ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Employee employee = employeeRepository.findEmployeeByIdAndStatus(employeeDTO.getId(), Status.ACTIVE);
        if (employee == null) {
            logger.warn("{}", ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (employeeDTO.getName() != null) {
            employee.setName(employeeDTO.getName());
        }
        if (employeeDTO.getDateOfBirth() != null) {
            employee.setDateOfBirth(employeeDTO.getDateOfBirth());
        }
        if (employeeDTO.getIdentifyNumber() != null) {
            employee.setIdentifyNumber(employeeDTO.getIdentifyNumber());
        }
        if (employeeDTO.getPhone() != null) {
            employee.setPhone(employeeDTO.getPhone());
        }
        if (employeeDTO.getEmail() != null) {
            employee.setEmail(employee.getEmail());
        }
        employeeRepository.save(employee);
        logger.info("Update profile successfully.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
