package com.backend.swp.apalary.service.Impl;

import com.backend.swp.apalary.model.constant.Status;
import com.backend.swp.apalary.model.dto.ApplicationDTO;
import com.backend.swp.apalary.model.entity.Application;
import com.backend.swp.apalary.model.entity.ApplicationType;
import com.backend.swp.apalary.model.entity.Employee;
import com.backend.swp.apalary.repository.ApplicationRepository;
import com.backend.swp.apalary.repository.ApplicationTypeRepository;
import com.backend.swp.apalary.repository.EmployeeRepository;
import com.backend.swp.apalary.service.ApplicationService;
import com.backend.swp.apalary.service.constant.ServiceMessage;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final EmployeeRepository employeeRepository;
    private final ApplicationTypeRepository applicationTypeRepository;
    private final ModelMapper modelMapper;

    private static final Logger logger = LogManager.getLogger(ApplicationServiceImpl.class);
    private static final String CREATE_APPLICATION_MESSAGE = "Create application: ";
    private static final String GET_APPLICATION_MESSAGE = "Get application: ";
    private static final String APPROVE_APPLICATION_MESSAGE = "Approve application: ";
    private static final String DISAPPROVE_APPLICATION_MESSAGE = "Disapprove application: ";

    @Override
    public ResponseEntity<Void> createApplication(ApplicationDTO applicationDTO, String employeeId) {
        logger.info("{}{}", CREATE_APPLICATION_MESSAGE, applicationDTO);
        if (applicationDTO == null) {
            logger.warn(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ApplicationType applicationType = applicationTypeRepository.findApplicationTypeById(applicationDTO.getApplicationTypeId());
        if (applicationType == null) {
            logger.warn(ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Employee employee = employeeRepository.findEmployeeByIdAndStatus(employeeId, Status.ACTIVE);
        if (employee == null) {
            logger.warn(ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Application application = modelMapper.map(applicationDTO, Application.class);
        application.setId(null);
        application.setStatus(Status.PROCESSING);
        application.setApplicationType(applicationType);
        application.setEmployee(employee);
        applicationRepository.save(application);
        logger.info("Create application successfully.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApplicationDTO> getApplicationById(Integer id) {
        logger.info("{}{}", GET_APPLICATION_MESSAGE, id);
        if (id == null) {
            logger.warn(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Application application = applicationRepository.findApplicationByIdAndStatus(id, Status.ACTIVE);
        if (application == null) {
            logger.warn(ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ApplicationDTO applicationDTO = modelMapper.map(application, ApplicationDTO.class);
        logger.info("Get application {} successfully.", id);
        return new ResponseEntity<>(applicationDTO, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<List<ApplicationDTO>> getAllApplication() {
        logger.info("{}{}", GET_APPLICATION_MESSAGE, "all");
        List<Application> applications = applicationRepository.findApplicationByStatus(Status.ACTIVE);
        List<ApplicationDTO> applicationDTOS = applications.stream().map(application -> modelMapper.map(application, ApplicationDTO.class)).toList();
        logger.info("Get all applications successfully.");
        return new ResponseEntity<>(applicationDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> approveApplication(Integer id) {
        logger.info("{}{}", APPROVE_APPLICATION_MESSAGE, id);
        if (id == null) {
            logger.warn(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Application application = applicationRepository.findApplicationByIdAndStatus(id, Status.PROCESSING);
        if (application == null) {
            logger.warn(ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        application.setStatus(Status.ACTIVE);
        applicationRepository.save(application);
        logger.info("Approve application id {} successfully.", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> disapproveApplication(Integer id) {
        logger.info("{}{}", DISAPPROVE_APPLICATION_MESSAGE, id);
        if (id == null) {
            logger.warn(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Application application = applicationRepository.findApplicationByIdAndStatus(id, Status.PROCESSING);
        if (application == null) {
            logger.warn(ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        application.setStatus(Status.INACTIVE);
        applicationRepository.save(application);
        logger.info("Disapprove application id {} successfully.", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
