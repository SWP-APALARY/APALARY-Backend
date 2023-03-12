package com.backend.swp.apalary.service.Impl;

import com.backend.swp.apalary.config.event.EmailEvent;
import com.backend.swp.apalary.model.constant.Status;
import com.backend.swp.apalary.model.dto.ApplicantDTO;
import com.backend.swp.apalary.model.entity.Applicant;
import com.backend.swp.apalary.model.entity.JobOffering;
import com.backend.swp.apalary.model.response.ApplicantResponseInList;
import com.backend.swp.apalary.repository.ApplicantRepository;
import com.backend.swp.apalary.repository.JobOfferingRepository;
import com.backend.swp.apalary.service.ApplicantService;
import com.backend.swp.apalary.service.constant.ServiceMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApplicantServiceImpl implements ApplicantService {
    final
    ApplicantRepository applicantRepository;
    final JobOfferingRepository jobOfferingRepository;
    final ModelMapper modelMapper;
    final ApplicationEventMulticaster applicationEventMulticaster;
    private static final Logger logger = LogManager.getLogger(ApplicantServiceImpl.class);
    private static final String CREATE_APPLICANT_MESSAGE = "Create applicant: ";
    private static final String GET_APPLICANT_MESSAGE = "Get applicant: ";

    public ApplicantServiceImpl(ApplicantRepository applicantRepository, JobOfferingRepository jobOfferingRepository, ModelMapper modelMapper, ApplicationEventMulticaster applicationEventMulticaster) {
        this.applicantRepository = applicantRepository;
        this.jobOfferingRepository = jobOfferingRepository;
        this.modelMapper = modelMapper;
        this.applicationEventMulticaster = applicationEventMulticaster;
    }

    @Override
    public ResponseEntity<Void> createApplicant(ApplicantDTO applicantDTO) {
        logger.info("{}{}", CREATE_APPLICANT_MESSAGE, applicantDTO);
        if (applicantDTO == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        JobOffering jobOffering = jobOfferingRepository.findJobOfferingByIdAndStatus(applicantDTO.getJobOfferingId(), Status.ACTIVE);
        if (jobOffering == null) {
            logger.warn("{}", ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Applicant applicant = modelMapper.map(applicantDTO, Applicant.class);
        applicant.setId(null);
        applicant.setStatus(Status.PROCESSING);
        applicant.setJobOffering(jobOffering);
        applicantRepository.save(applicant);
        logger.info("Applicant register successfully.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<List<ApplicantResponseInList>> getAllProcessingApplicant() {
        logger.info("{}{}", GET_APPLICANT_MESSAGE, "all");
        List<Applicant> applicants = applicantRepository.findApplicantByStatus(Status.PROCESSING);
        List<ApplicantResponseInList> applicantDTOS = applicants.stream().map(applicant -> modelMapper.map(applicant, ApplicantResponseInList.class)
        ).toList();
        logger.info("Get all applicants successfully.");
        return new ResponseEntity<>(applicantDTOS, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<List<ApplicantResponseInList>> getAllApplicantOfAJobOffering(Integer jobOfferingId) {
        logger.info("{}Job Offering id: {}", GET_APPLICANT_MESSAGE, jobOfferingId);
        if (jobOfferingId == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        JobOffering jobOffering = jobOfferingRepository.findJobOfferingByIdAndStatus(jobOfferingId, Status.ACTIVE);
        if (jobOffering == null) {
            logger.warn("{}", ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Applicant> applicants = applicantRepository.findApplicantByStatusAndJobOffering(Status.PROCESSING, jobOffering);
        List<ApplicantResponseInList> applicantDTOS = applicants.stream().map(applicant -> modelMapper.map(applicant, ApplicantResponseInList.class)
        ).toList();
        logger.info("Get all applicants of a job offering id {} successfully.", jobOfferingId);
        return new ResponseEntity<>(applicantDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApplicantDTO> getApplicantById(Integer id) {
        logger.info("{}{}", GET_APPLICANT_MESSAGE, id);
        if (id == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Applicant applicant = applicantRepository.findApplicantById(id);
        if (applicant == null) {
            logger.warn("{}", ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ApplicantDTO applicantDTO = modelMapper.map(applicant, ApplicantDTO.class);
        logger.info("{}{} successfully", GET_APPLICANT_MESSAGE, id);
        return new ResponseEntity<>(applicantDTO, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<List<ApplicantResponseInList>> getAcceptedApplicant() {
        logger.info("{}{}", GET_APPLICANT_MESSAGE, "all accepted applicant");
        List<Applicant> applicants = applicantRepository.findApplicantByStatus(Status.ACTIVE);
        List<ApplicantResponseInList> applicantDTOS = applicants.stream().map(applicant -> modelMapper.map(applicant, ApplicantResponseInList.class)
        ).toList();
        logger.info("Get all accepted applicants successfully.");
        return new ResponseEntity<>(applicantDTOS, HttpStatus.OK);
    }
    @Transactional
    @Override
    public ResponseEntity<List<ApplicantResponseInList>> getRejectedApplicant() {
        logger.info("{}{}", GET_APPLICANT_MESSAGE, "all rejected applicant");
        List<Applicant> applicants = applicantRepository.findApplicantByStatus(Status.INACTIVE);
        List<ApplicantResponseInList> applicantDTOS = applicants.stream().map(applicant -> modelMapper.map(applicant, ApplicantResponseInList.class)
        ).toList();
        logger.info("Get all rejected applicants successfully.");
        return new ResponseEntity<>(applicantDTOS, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Void> acceptApplicant(Integer id, boolean isAccepted) {
        if (isAccepted) logger.info("Accept applicant id: {}", id);
        else logger.info("Reject applicant id: {}", id);
        if (id == null) {
            logger.warn("{}", ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Applicant applicant = applicantRepository.findApplicantByIdAndStatus(id, Status.PROCESSING);
        if (applicant == null) {
            logger.warn("{}", ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (isAccepted) applicant.setStatus(Status.ACTIVE);
        else applicant.setStatus(Status.INACTIVE);
        applicantRepository.save(applicant);
        applicationEventMulticaster.multicastEvent(new EmailEvent(this, isAccepted, applicant.getName(), applicant.getEmail()));
        JobOffering jobOffering = applicant.getJobOffering();
        int countEmployee = applicantRepository.countApplicantByJobOfferingAndStatus(jobOffering, Status.ACTIVE);
        if (countEmployee == jobOffering.getMaxEmployee()) {
            jobOffering.setStatus(Status.INACTIVE);
            jobOfferingRepository.save(jobOffering);
        }
        logger.info("Successful.");

        return new ResponseEntity<>(HttpStatus.OK);
    }




}
