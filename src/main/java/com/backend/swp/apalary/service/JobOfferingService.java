package com.backend.swp.apalary.service;

import com.backend.swp.apalary.model.constant.Status;
import com.backend.swp.apalary.model.dto.JobOfferingDTO;
import com.backend.swp.apalary.model.entity.Department;
import com.backend.swp.apalary.model.entity.Employee;
import com.backend.swp.apalary.model.entity.JobOffering;
import com.backend.swp.apalary.repository.DepartmentRepository;
import com.backend.swp.apalary.repository.EmployeeRepository;
import com.backend.swp.apalary.repository.JobOfferingRepository;
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
public class JobOfferingService {
    final JobOfferingRepository jobOfferingRepository;
    final EmployeeRepository employeeRepository;
    final DepartmentRepository departmentRepository;
    final ModelMapper modelMapper;
    private static final Logger logger = LogManager.getLogger(JobOfferingService.class);
    private final static String CREATE_JOB_OFFERING_MESSAGE = "Create job offering: ";
    private final static String APPROVE_JOB_OFFERING_MESSAGE = "Approve job offering: ";
    private final static String DISAPPROVE_JOB_OFFERING_MESSAGE = "Disapprove job offering: ";
    private final static String GET_JOB_OFFERING_MESSAGE = "Get job offering: ";
    private final static String UPDATE_JOB_OFFERING_MESSAGE = "Update job offering: ";
    private final static String DELETE_JOB_OFFERING_MESSAGE = "Delete job offering: ";
    public JobOfferingService(JobOfferingRepository jobOfferingRepository, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.jobOfferingRepository = jobOfferingRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<Void> createJobOffering(JobOfferingDTO jobOfferingDTO) {
        logger.info("{}{}", CREATE_JOB_OFFERING_MESSAGE, jobOfferingDTO);
        if (jobOfferingDTO == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Department department = departmentRepository.findDepartmentById(jobOfferingDTO.getDepartmentId());
        if (department == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Employee employee = employeeRepository.findEmployeeById(jobOfferingDTO.getEmployeeId());
        if (employee == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        JobOffering jobOffering = modelMapper.map(jobOfferingDTO, JobOffering.class);
        jobOffering.setId(null);
        jobOffering.setStatus(Status.PROCESSING);
        jobOffering.setDepartment(department);
        jobOffering.setEmployee(employee);
        jobOfferingRepository.save(jobOffering);
        logger.info("{}{}", CREATE_JOB_OFFERING_MESSAGE, "successfully");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> approveJobOffering(Integer id) {
        logger.info("{}{}", APPROVE_JOB_OFFERING_MESSAGE, id);
        if (id == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        JobOffering jobOffering = jobOfferingRepository.findJobOfferingByIdAndStatus(id, Status.PROCESSING);
        if (jobOffering == null) {
            logger.warn("{}", ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        jobOffering.setStatus(Status.ACTIVE);
        jobOfferingRepository.save(jobOffering);
        logger.info("{}{}", APPROVE_JOB_OFFERING_MESSAGE, "successfully");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<Void> disApproveJobOffering(Integer id) {
        logger.info("{}{}", DISAPPROVE_JOB_OFFERING_MESSAGE, id);
        if (id == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        JobOffering jobOffering = jobOfferingRepository.findJobOfferingByIdAndStatus(id, Status.PROCESSING);
        if (jobOffering == null) {
            logger.warn("{}", ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        jobOffering.setStatus(Status.INACTIVE);
        jobOfferingRepository.save(jobOffering);
        logger.info("{}{}", DISAPPROVE_JOB_OFFERING_MESSAGE, "successfully");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Void> approveAll() {
        logger.info("{}{}", APPROVE_JOB_OFFERING_MESSAGE, "all");
        List<JobOffering> jobOfferings = jobOfferingRepository.findJobOfferingByStatus(Status.PROCESSING);
        jobOfferings.forEach(jobOffering -> jobOffering.setStatus(Status.ACTIVE));
        logger.info("Approve all job offering successfully.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Void> disApproveAll() {
        logger.info("{}{}", DISAPPROVE_JOB_OFFERING_MESSAGE, "all");
        List<JobOffering> jobOfferings = jobOfferingRepository.findJobOfferingByStatus(Status.PROCESSING);
        jobOfferings.forEach(jobOffering -> jobOffering.setStatus(Status.INACTIVE));
        logger.info("Approve all job offering successfully.");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<List<JobOfferingDTO>> getAllJobOfferings() {
        logger.info("{}{}", GET_JOB_OFFERING_MESSAGE, "all");
        List<JobOffering> jobOfferings = jobOfferingRepository.findJobOfferingByStatus(Status.ACTIVE);
        List<JobOfferingDTO> jobOfferingDTOS = jobOfferings.stream().map(jobOffering -> modelMapper.map(jobOffering, JobOfferingDTO.class)).toList();
        logger.info("Get all job offering successfully.");
        return new ResponseEntity<>(jobOfferingDTOS, HttpStatus.OK);
    }

    public ResponseEntity<JobOfferingDTO> getJobOfferingById(Integer id) {
        logger.info("{}{}", GET_JOB_OFFERING_MESSAGE, id);
        if (id == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        JobOffering jobOffering = jobOfferingRepository.findJobOfferingByIdAndStatus(id, Status.ACTIVE);
        if (jobOffering == null) {
            logger.warn("{}", ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        JobOfferingDTO jobOfferingDTO = modelMapper.map(jobOffering, JobOfferingDTO.class);
        logger.info("Get job offering id {} successfully", id);
        return new ResponseEntity<>(jobOfferingDTO, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateJobOffering(JobOfferingDTO jobOfferingDTO) {
        logger.info("{}{}", UPDATE_JOB_OFFERING_MESSAGE, jobOfferingDTO);
        if (jobOfferingDTO == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        JobOffering jobOffering = jobOfferingRepository.findJobOfferingByIdAndStatus(jobOfferingDTO.getId(), Status.ACTIVE);
        if (jobOffering == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (jobOfferingDTO.getId() != null) {
            jobOffering.setId(jobOfferingDTO.getId());
        }
        if (jobOfferingDTO.getTitle() != null) {
            jobOffering.setTitle(jobOfferingDTO.getTitle());
        }
        if (jobOfferingDTO.getDescription() != null) {
            jobOffering.setDescription(jobOfferingDTO.getDescription());
        }
        if (jobOfferingDTO.getBaseSalary() != null) {
            jobOffering.setBaseSalary(jobOffering.getBaseSalary());
        }
        if (jobOfferingDTO.getStatus() != null) {
            jobOffering.setStatus(jobOfferingDTO.getStatus());
        }
        if (jobOfferingDTO.getMaxEmployee() != null) {
            jobOffering.setMaxEmployee(jobOfferingDTO.getMaxEmployee());
        }
        if (jobOfferingDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findDepartmentById(jobOfferingDTO.getId());
            if (department == null) {
                logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            jobOffering.setDepartment(department);
        }
        if (jobOfferingDTO.getEmployeeId() != null) {
            Employee employee = employeeRepository.findEmployeeById(jobOfferingDTO.getEmployeeId());
            if (employee == null) {
                logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            jobOffering.setEmployee(employee);
        }
        jobOfferingRepository.save(jobOffering);
        logger.info("Update job offering successfully.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteJobOffering(Integer id) {
        logger.info("{}{}", DELETE_JOB_OFFERING_MESSAGE, id);
        if (id == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        JobOffering jobOffering = jobOfferingRepository.findJobOfferingByIdAndStatus(id, Status.ACTIVE);
        if (jobOffering == null) {
            logger.warn("{}", ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        jobOffering.setStatus(Status.INACTIVE);
        jobOfferingRepository.save(jobOffering);
        logger.info("Delete job offering id {} successfully.", id );
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
