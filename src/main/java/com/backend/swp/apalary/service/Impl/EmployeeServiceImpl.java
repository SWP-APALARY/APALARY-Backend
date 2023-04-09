package com.backend.swp.apalary.service.Impl;

import com.backend.swp.apalary.config.exception.BadRequestException;
import com.backend.swp.apalary.model.constant.Role;
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
            logger.warn("{}" , ServiceMessage.INVALID_ARGUMENT_MESSAGE.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee == null) {
            logger.warn("{}", ServiceMessage.ID_NOT_EXIST_MESSAGE.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        logger.info("Get employee id {} successfully.", id);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<List<EmployeeDTO>> getAllActiveEmployee(String employeeId, Role role) {
        logger.info("{}{}", GET_EMPLOYEE_MESSAGE, "all active");
        List<EmployeeDTO> employeeDTOS = getEmployeeByStatus(employeeId, role, Status.ACTIVE);
        logger.info("Get all active employee successfully.");
        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);
    }
    @Override
    @Transactional
    public ResponseEntity<List<EmployeeDTO>> getAllInactiveEmployee(String employeeId, Role role) {
        logger.info("{}{}" ,GET_EMPLOYEE_MESSAGE, "all inactive");
        List<EmployeeDTO> employeeDTOS = getEmployeeByStatus(employeeId, role, Status.INACTIVE);
        logger.info("Get all inactive employee successfully.");
        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee(String employeeId, Role role) {
        logger.info("{}{}" ,GET_EMPLOYEE_MESSAGE, "all");
        Employee currentEmployee = employeeRepository.findEmployeeByIdAndStatus(employeeId, Status.ACTIVE);
        List<Employee> employees = employeeRepository.findAll();
        if (role.equals(Role.MANAGER)) {
            employees = employees.stream().filter(employee -> employee.getDepartment().equals(currentEmployee.getDepartment())).toList();
        }
        else if (role.equals(Role.RESIDENT)) {
            employees = employees.stream().filter(employee -> employee.getRole().equals(Role.EMPLOYEE)).toList();
        }
        List<EmployeeDTO> employeeDTOS = employees.stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class)).toList();
        logger.info("Get all inactive employee successfully.");
        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);
    }

    private List<EmployeeDTO> getEmployeeByStatus(String employeeId, Role role, Status status) {
        Employee currentEmployee = employeeRepository.findEmployeeByIdAndStatus(employeeId, Status.ACTIVE);
        List<Employee> employees = employeeRepository.findEmployeeByStatus(status);
        if (role.equals(Role.MANAGER)) {
            employees = employees.stream().filter(employee -> employee.getDepartment().equals(currentEmployee.getDepartment())).toList();
        }
        else if (role.equals(Role.RESIDENT)) {
            employees = employees.stream().filter(employee -> employee.getRole().equals(Role.EMPLOYEE)).toList();
        }
        return employees.stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class)).toList();
    }

    @Override
    public ResponseEntity<Void> updateEmployee(EmployeeDTO employeeDTO, String userId) {
        logger.info("{}{}", UPDATE_EMPLOYEE_MESSAGE, employeeDTO);
        if (employeeDTO == null) {
            logger.warn("{}" , ServiceMessage.INVALID_ARGUMENT_MESSAGE.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Employee employee = employeeRepository.findEmployeeByIdAndStatus(userId, Status.ACTIVE);
        if (employee == null) {
            logger.warn("{}", ServiceMessage.ID_NOT_EXIST_MESSAGE.getMessage());
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
            employee.setEmail(employeeDTO.getEmail());
        }
        employeeRepository.save(employee);
        logger.info("Update profile successfully.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> removeEmployee(String employeeId, String userId) throws BadRequestException {
        if (employeeId == null) {
            logger.warn("{}" , ServiceMessage.INVALID_ARGUMENT_MESSAGE.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (employeeId.equals(userId)) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE.getMessage());
            throw new BadRequestException("Cannot remove your account.");
        }
        Employee employee = employeeRepository.findEmployeeByIdAndStatus(employeeId, Status.ACTIVE);
        if (employee == null) {
            logger.warn("{}", ServiceMessage.ID_NOT_EXIST_MESSAGE.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        employee.setStatus(Status.INACTIVE);
        employeeRepository.save(employee);
        logger.info("Ban employee id {} successfully.", employeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> recoverEmployee(String employeeId) {
        if (employeeId == null) {
            logger.warn("{}" , ServiceMessage.INVALID_ARGUMENT_MESSAGE.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Employee employee = employeeRepository.findEmployeeByIdAndStatus(employeeId, Status.INACTIVE);
        if (employee == null) {
            logger.warn("{}", ServiceMessage.ID_NOT_EXIST_MESSAGE.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        employee.setStatus(Status.ACTIVE);
        employee.getContract().setStatus(Status.INACTIVE);
        employeeRepository.save(employee);
        logger.info("Ban employee id {} successfully.", employeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
