package com.backend.swp.apalary.service.Impl;

import com.backend.swp.apalary.config.event.UserEvent;
import com.backend.swp.apalary.config.exception.BadRequestException;
import com.backend.swp.apalary.model.constant.Role;
import com.backend.swp.apalary.model.constant.Status;
import com.backend.swp.apalary.model.entity.Contract;
import com.backend.swp.apalary.model.entity.Employee;
import com.backend.swp.apalary.model.entity.Resident;
import com.backend.swp.apalary.model.request.ChangePasswordRequest;
import com.backend.swp.apalary.model.request.CreateEmployeeRequest;
import com.backend.swp.apalary.model.request.CreateResidentRequest;
import com.backend.swp.apalary.model.request.LoginRequest;
import com.backend.swp.apalary.model.response.AuthResponse;
import com.backend.swp.apalary.repository.*;
import com.backend.swp.apalary.service.constant.ServiceMessage;
import com.backend.swp.apalary.service.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements com.backend.swp.apalary.service.AuthService {
    private final EmployeeRepository employeeRepository;
    private final ResidentRepository residentRepository;
    private final ContractRepository contractRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final ApplicationEventMulticaster applicationEventMulticaster;
    private static final Logger logger = LogManager.getLogger(AuthServiceImpl.class);
    private static final String CREATE_EMPLOYEE_MESSAGE = "Create employee: ";
    private static final String CREATE_RESIDENT_MESSAGE = "Create resident: ";

    public ResponseEntity<AuthResponse> createEmployee(CreateEmployeeRequest employeeDTO) throws BadRequestException {
        logger.info("{}{}", CREATE_EMPLOYEE_MESSAGE, employeeDTO.getUsername());
        Contract contract = contractRepository.findContractByIdAndStatus(employeeDTO.getContractId(), Status.ACTIVE);
        if (contract == null) {
            logger.warn(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            logger.warn("Email is already exists.");
            throw new BadRequestException("Email is already exists.");
        }
        if (employeeRepository.existsByIdentifyNumber(employeeDTO.getIdentifyNumber())) {
            logger.warn("Identify number is already exists.");
            throw new BadRequestException("Identify number is already exists.");
        }
        if (employeeRepository.existsByUsername(employeeDTO.getUsername())) {
            logger.warn("Username is already exists.");
            throw new BadRequestException("Username is already exists.");
        }
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        employee.setId(createEmployeeId(employee.getRole()));
        employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        employee.setLink("/feedback/" + employee.getId());
        employee.setContract(contract);
        employee.setStatus(Status.ACTIVE);
        employeeRepository.save(employee);
        String jwt = jwtService.generateToken(employee);
        String role = employee.getAuthorities().toString();
        logger.info("Create employee successfully.");
        applicationEventMulticaster.multicastEvent(new UserEvent(this, employee.getName(), employee.getEmail(), employee.getUsername(), employeeDTO.getPassword()));
        return new ResponseEntity<>(AuthResponse.builder().token(jwt).role(role.substring(6, role.length() - 1)).build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AuthResponse> changeEmployeePassword(ChangePasswordRequest changePasswordRequest, String userId) throws BadRequestException{
        logger.info("Change password for employee ");
        if (changePasswordRequest == null) {
            logger.warn(ServiceMessage.INVALID_ARGUMENT_MESSAGE.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Employee employee = employeeRepository.findEmployeeByIdAndStatus(userId, Status.ACTIVE);
        UserDetails userDetails = employee != null ? employee : residentRepository.findResidentByIdAndStatus(userId, Status.ACTIVE);
        if (userDetails == null) {
            logger.warn(ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), userDetails.getPassword())) {
            if (changePasswordRequest.getNewPassword() == null || changePasswordRequest.getNewPassword().equals("")) {
                logger.warn(ServiceMessage.INVALID_ARGUMENT_MESSAGE.getMessage());
                throw new BadRequestException("New password is invalid.");
            }
            if (userDetails instanceof Employee) {
                ((Employee) userDetails).setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
                employeeRepository.save((Employee) userDetails);

                applicationEventMulticaster.multicastEvent(new UserEvent(this, ((Employee) userDetails).getName(), ((Employee) userDetails).getEmail()));
            }
            else {
                ((Resident) userDetails).setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
                residentRepository.save((Resident) userDetails);
                applicationEventMulticaster.multicastEvent(new UserEvent(this, ((Resident) userDetails).getName(), ((Resident) userDetails).getEmail()));
            }
        } else {
            logger.warn(ServiceMessage.INVALID_ARGUMENT_MESSAGE.getMessage());
            throw new BadRequestException("Old password is not matched.");
        }
        String jwt = jwtService.generateToken(userDetails);
        String role = userDetails.getAuthorities().toString();

        logger.info("Change password successfully.");
        return new ResponseEntity<>(AuthResponse.builder().token(jwt).role(role.substring(6, role.length() - 1)).build(), HttpStatus.OK);

    }
    public ResponseEntity<AuthResponse> createResident(CreateResidentRequest residentDTO) throws BadRequestException {
        logger.info("{}{}", CREATE_RESIDENT_MESSAGE, residentDTO.getUsername());
        if (residentRepository.existsByEmail(residentDTO.getEmail())) {
            logger.warn("Email is already exists.");
            throw new BadRequestException("Email is already exists.");
        }
        if (residentRepository.existsByIdentifyNumber(residentDTO.getIdentifyNumber())) {
            logger.warn("Identify number is already exists.");
            throw new BadRequestException("Identify number is already exists.");
        }
        if (residentRepository.existsByUsername(residentDTO.getUsername())) {
            logger.warn("Username is already exists.");
            throw new BadRequestException("Username is already exists.");
        }
        Resident resident = modelMapper.map(residentDTO, Resident.class);
        resident.setId(createResidentId());
        resident.setPassword(passwordEncoder.encode(residentDTO.getPassword()));
        resident.setStatus(Status.ACTIVE);
        residentRepository.save(resident);
        String jwt = jwtService.generateToken(resident);
        String role = resident.getAuthorities().toString();
        logger.info("Create resident id {} successfully.", residentDTO.getId());
       // applicationEventMulticaster.multicastEvent(new UserEvent(this, resident.getName(), resident.getEmail(), resident.getUsername(), residentDTO.getPassword()));
        return new ResponseEntity<>(AuthResponse.builder().token(jwt).role(role.substring(6, role.length() - 1)).build(), HttpStatus.OK);
    }

    public ResponseEntity<AuthResponse> login(LoginRequest userLogin) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword())
        );
        Employee employee = employeeRepository.findEmployeeByUsernameAndStatus(userLogin.getUsername(), Status.ACTIVE);
        UserDetails userDetails = employee != null ? employee : residentRepository.findResidentByUsernameAndStatus(userLogin.getUsername(), Status.ACTIVE);
        if (userDetails == null) {
            logger.warn(ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        String jwt = jwtService.generateToken(userDetails);
        String role = userDetails.getAuthorities().toString();
        String avatar = "";
        if (userDetails instanceof Employee) {
            avatar = ((Employee) userDetails).getAvatar();
        }
        logger.info("Login successfully.");
        return new ResponseEntity<>(AuthResponse.builder().token(jwt).role(role.substring(6, role.length() - 1)).avatar(avatar).build(), HttpStatus.OK);
    }

    private String createEmployeeId(Role role) {
        String id = null;
        if (role.equals(Role.HR_EMPLOYEE) || role.equals(Role.HR_MANAGER)) {
            String lastEmployeeId = employeeRepository.lastEmployeeBeginWith("HR%");
            id = Utils.generateIdWithPrefix(Objects.requireNonNullElse(lastEmployeeId, "HR0000"));
        }
        else if (role.equals(Role.HEAD_MANAGER))  {
            String lastEmployeeId = employeeRepository.lastEmployeeBeginWith("HM%");
            id = Utils.generateIdWithPrefix(Objects.requireNonNullElse(lastEmployeeId, "HM0000"));
        }
        else if (role.equals(Role.MANAGER))  {
            String lastEmployeeId = employeeRepository.lastEmployeeBeginWith("MN%");
            id = Utils.generateIdWithPrefix(Objects.requireNonNullElse(lastEmployeeId, "MN0000"));
        }
        else if (role.equals(Role.EMPLOYEE))  {
            String lastEmployeeId = employeeRepository.lastEmployeeBeginWith("EP%");
            id = Utils.generateIdWithPrefix(Objects.requireNonNullElse(lastEmployeeId, "EP0000"));
        }
        return id;
    }

    private String createResidentId() {
        String lastResidentId = residentRepository.lastResidentId();
        return Utils.generateIdWithPrefix(Objects.requireNonNullElse(lastResidentId, "RE0000"));
    }

}
