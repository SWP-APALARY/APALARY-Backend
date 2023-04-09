package com.backend.swp.apalary.service.Impl;

import com.backend.swp.apalary.model.constant.Role;
import com.backend.swp.apalary.model.constant.Status;
import com.backend.swp.apalary.model.dto.ApplicationDTO;
import com.backend.swp.apalary.model.entity.Application;
import com.backend.swp.apalary.model.entity.ApplicationType;
import com.backend.swp.apalary.model.entity.Department;
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
        if (applicationDTO.getApplicationTypeId() == 4) application.setStatus(Status.ACTIVE);
        else application.setStatus(Status.PROCESSING);
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
        Application application = applicationRepository.findApplicationById(id);
        if (application == null) {
            logger.warn(ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ApplicationDTO applicationDTO = modelMapper.map(application, ApplicationDTO.class);
        logger.info("Get application {} successfully.", id);
        return new ResponseEntity<>(applicationDTO, HttpStatus.OK);
    }
    @Transactional
    @Override
    public ResponseEntity<List<ApplicationDTO>> getProcessingSalaryIncreaseApplicationsForHeadManager(String userId, Role role) {
        logger.info("{}{}", GET_APPLICATION_MESSAGE, "Processing Salary Increase application for head manager");
        List<ApplicationDTO> applicationDTOS = getApplicationByStatusAndApplicationType(Status.PROCESSING_2, 1, userId, role);
        logger.info("Get processing Salary Increase application for head manager successfully.");
        return new ResponseEntity<>(applicationDTOS, HttpStatus.OK);

    }

    @Transactional
    @Override
    public ResponseEntity<List<ApplicationDTO>> getProcessingSalaryIncreaseApplications(String userId, Role role) {
        logger.info("{}{}", GET_APPLICATION_MESSAGE, "Processing Salary Increase application");
        List<ApplicationDTO> applicationDTOS = getApplicationByStatusAndApplicationType(Status.PROCESSING, 1, userId, role);
        logger.info("Get processing Salary Increase application successfully.");
        return new ResponseEntity<>(applicationDTOS, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<List<ApplicationDTO>> getActiveSalaryIncreaseApplication(String userId, Role role) {
        logger.info("{}{}", GET_APPLICATION_MESSAGE, "Active Salary Increase application");
        List<ApplicationDTO> applicationDTOS = getApplicationByStatusAndApplicationType(Status.ACTIVE, 1, userId, role);
        logger.info("Get active Salary Increase application successfully.");
        return new ResponseEntity<>(applicationDTOS, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<List<ApplicationDTO>> getInactiveSalaryIncreaseApplication(String userId, Role role) {
        logger.info("{}{}", GET_APPLICATION_MESSAGE, "Inactive Salary Increase application");
        List<ApplicationDTO> applicationDTOS = getApplicationByStatusAndApplicationType(Status.INACTIVE, 1, userId, role);
        logger.info("Get inactive Salary Increase application successfully.");
        return new ResponseEntity<>(applicationDTOS, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<List<ApplicationDTO>> getProcessingDayLeaveApplication(String userId, Role role) {
        logger.info("{}{}", GET_APPLICATION_MESSAGE, "Processing Day leave application");
        List<ApplicationDTO> applicationDTOS = getApplicationByStatusAndApplicationType(Status.PROCESSING, 2, userId, role);
        logger.info("Get processing day leave application successfully.");
        return new ResponseEntity<>(applicationDTOS, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<List<ApplicationDTO>> getActiveDayLeaveApplication(String userId, Role role) {
        logger.info("{}{}", GET_APPLICATION_MESSAGE, "Active Day leave application");
        List<ApplicationDTO> applicationDTOS = getApplicationByStatusAndApplicationType(Status.ACTIVE, 2, userId, role);
        logger.info("Get active day leave application successfully.");
        return new ResponseEntity<>(applicationDTOS, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<List<ApplicationDTO>> getInactiveDayLeaveApplication(String userId, Role role) {
        logger.info("{}{}", GET_APPLICATION_MESSAGE, "Inactive Day Leave application");
        List<ApplicationDTO> applicationDTOS = getApplicationByStatusAndApplicationType(Status.INACTIVE, 2, userId, role);
        logger.info("Get inactive Day Leave application successfully.");
        return new ResponseEntity<>(applicationDTOS, HttpStatus.OK);
    }


    @Transactional
    @Override
    public ResponseEntity<List<ApplicationDTO>> getProcessingRecruitment(String userId, Role role) {
        logger.info("{}{}", GET_APPLICATION_MESSAGE, "Processing recruitment application");
        List<ApplicationDTO> applicationDTOS = getApplicationByStatusAndApplicationType(Status.PROCESSING, 3, userId, role);
        logger.info("Get processing recruitment application successfully.");
        return new ResponseEntity<>(applicationDTOS, HttpStatus.OK);
    }


    @Transactional
    @Override
    public ResponseEntity<List<ApplicationDTO>> getActiveRecruitmentApplication(String userId, Role role) {
        logger.info("{}{}", GET_APPLICATION_MESSAGE, "Active recruitment application");
        List<ApplicationDTO> applicationDTOS = getApplicationByStatusAndApplicationType(Status.ACTIVE, 3, userId, role);
        logger.info("Get active recruitment application successfully.");
        return new ResponseEntity<>(applicationDTOS, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<List<ApplicationDTO>> getInactiveRecruitmentApplication(String userId, Role role) {
        logger.info("{}{}", GET_APPLICATION_MESSAGE, "Inactive recruitment application");
        List<ApplicationDTO> applicationDTOS = getApplicationByStatusAndApplicationType(Status.INACTIVE, 3, userId, role);
        logger.info("Get inactive recruitment application successfully.");
        return new ResponseEntity<>(applicationDTOS, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<List<ApplicationDTO>> getProcessingReport(String userId, Role role) {
        logger.info("{}{}", GET_APPLICATION_MESSAGE, "Processing Report application");
        List<ApplicationDTO> applicationDTOS = getApplicationByStatusAndApplicationType(Status.PROCESSING, 4, userId, role);
        logger.info("Get processing report application successfully.");
        return new ResponseEntity<>(applicationDTOS, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<List<ApplicationDTO>> getActiveReport(String userId, Role role) {
        logger.info("{}{}", GET_APPLICATION_MESSAGE, "Active Report application");
        List<ApplicationDTO> applicationDTOS = getApplicationByStatusAndApplicationType(Status.ACTIVE, 4, userId, role);
        logger.info("Get active report application successfully.");
        return new ResponseEntity<>(applicationDTOS, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<List<ApplicationDTO>> getInactiveReport(String userId, Role role) {
        logger.info("{}{}", GET_APPLICATION_MESSAGE, "Inactive report application");
        List<ApplicationDTO> applicationDTOS = getApplicationByStatusAndApplicationType(Status.INACTIVE, 4, userId, role);
        logger.info("Get inactive report application successfully.");
        return new ResponseEntity<>(applicationDTOS, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<List<ApplicationDTO>> getAllApplicationOfCurrentEmployee(String employeeId) {
        logger.info("{}{}{}", GET_APPLICATION_MESSAGE, "all applications of current employee with id: ", employeeId);
        List<Application> applications = applicationRepository.findApplicationByEmployeeId(employeeId);
        List<ApplicationDTO> applicationDTOS = applications.stream().map(application -> modelMapper.map(application, ApplicationDTO.class)).toList();
        logger.info("Get all application of employee {} successfully.", employeeId);
        return new ResponseEntity<>(applicationDTOS, HttpStatus.OK);
    }
    private List<ApplicationDTO> getApplicationByStatusAndApplicationType(Status status, int applicationTypeId, String userId, Role role) {
        List<Application> applications = applicationRepository.findApplicationByStatusAndApplicationTypeId(status, applicationTypeId);
        Employee employee = employeeRepository.findEmployeeByIdAndStatus(userId, Status.ACTIVE);
        Department department = employee.getDepartment();
        if (role.equals(Role.MANAGER)) {
            applications = applications.stream().filter(application -> application.getDestinationEmployee().getDepartment().equals(department)).toList();
        }
        return applications.stream().map(application -> modelMapper.map(application, ApplicationDTO.class)).toList();
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
        if (!application.getApplicationType().getId().equals(1)) application.setStatus(Status.ACTIVE);
        else application.setStatus(Status.PROCESSING_2);
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

    @Override
    public ResponseEntity<Void> approveApplicationByHeadManager(Integer id) {
        logger.info("{}by head manager {}", APPROVE_APPLICATION_MESSAGE, id);
        if (id == null) {
            logger.warn(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Application application = applicationRepository.findApplicationByIdAndStatus(id, Status.PROCESSING_2);
        if (application == null) {
            logger.warn(ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        application.setStatus(Status.ACTIVE);
        applicationRepository.save(application);
        logger.info("Approve application by head manager id {} successfully.", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> disapproveApplicationByHeadManager(Integer id) {
        logger.info("{} by head manager {}", DISAPPROVE_APPLICATION_MESSAGE, id);
        if (id == null) {
            logger.warn(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Application application = applicationRepository.findApplicationByIdAndStatus(id, Status.PROCESSING_2);
        if (application == null) {
            logger.warn(ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        application.setStatus(Status.INACTIVE);
        applicationRepository.save(application);
        logger.info("Disapprove application head manager id {} successfully.", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
