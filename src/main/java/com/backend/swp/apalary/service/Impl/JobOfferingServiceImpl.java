package com.backend.swp.apalary.service.Impl;

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
public class JobOfferingServiceImpl implements com.backend.swp.apalary.service.JobOfferingService {
    final JobOfferingRepository jobOfferingRepository;
    final EmployeeRepository employeeRepository;
    final DepartmentRepository departmentRepository;
    final ModelMapper modelMapper;
    private static final Logger logger = LogManager.getLogger(JobOfferingServiceImpl.class);
    private final static String CREATE_JOB_OFFERING_MESSAGE = "Create job offering: ";
    private final static String GET_JOB_OFFERING_MESSAGE = "Get job offering: ";
    private final static String UPDATE_JOB_OFFERING_MESSAGE = "Update job offering: ";
    private final static String DELETE_JOB_OFFERING_MESSAGE = "Delete job offering: ";
    public JobOfferingServiceImpl(JobOfferingRepository jobOfferingRepository, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.jobOfferingRepository = jobOfferingRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public ResponseEntity<Void> createJobOffering(JobOfferingDTO jobOfferingDTO, String employeeId) {
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
        Employee employee = employeeRepository.findEmployeeByIdAndStatus(employeeId, Status.ACTIVE);
        if (employee == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        JobOffering jobOffering = modelMapper.map(jobOfferingDTO, JobOffering.class);
        jobOffering.setId(null);
        jobOffering.setStatus(Status.ACTIVE);
        jobOffering.setDepartment(department);
        jobOffering.setEmployee(employee);
        jobOfferingRepository.save(jobOffering);
        logger.info("{}{}", CREATE_JOB_OFFERING_MESSAGE, "successfully");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<List<JobOfferingDTO>> getAllJobOfferings() {
        logger.info("{}{}", GET_JOB_OFFERING_MESSAGE, "all");
        List<JobOffering> jobOfferings = jobOfferingRepository.findJobOfferingByStatus(Status.ACTIVE);
        List<JobOfferingDTO> jobOfferingDTOS = jobOfferings.stream().map(jobOffering -> modelMapper.map(jobOffering, JobOfferingDTO.class)).toList();
        logger.info("Get all job offering successfully.");
        return new ResponseEntity<>(jobOfferingDTOS, HttpStatus.OK);
    }

    @Override
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

    @Override
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
            jobOffering.setBaseSalary(jobOfferingDTO.getBaseSalary());
        }
        if (jobOfferingDTO.getStatus() != null) {
            jobOffering.setStatus(jobOfferingDTO.getStatus());
        }
        if (jobOfferingDTO.getMaxEmployee() != null) {
            jobOffering.setMaxEmployee(jobOfferingDTO.getMaxEmployee());
        }
        if (jobOfferingDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findDepartmentById(jobOfferingDTO.getDepartmentId());
            if (department == null) {
                logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            jobOffering.setDepartment(department);
        }
        if (jobOfferingDTO.getEmployeeId() != null) {
            Employee employee = employeeRepository.findEmployeeByIdAndStatus(jobOfferingDTO.getEmployeeId(), Status.ACTIVE);
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

    @Override
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
