package com.backend.swp.apalary.service;

import com.backend.swp.apalary.model.dto.ApplicationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ApplicationService {

    ResponseEntity<Void> createApplication(ApplicationDTO applicationDTO, String employeeId);

    ResponseEntity<ApplicationDTO> getApplicationById(Integer id);

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getProcessingSalaryIncreaseApplicationsForHeadManager();

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getProcessingSalaryIncreaseApplications();

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getActiveSalaryIncreaseApplication();

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getInactiveSalaryIncreaseApplication();

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getProcessingDayLeaveApplication();

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getActiveDayLeaveApplication();

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getInactiveDayLeaveApplication();

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getProcessingRecruitment();

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getActiveRecruitmentApplication();

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getInactiveRecruitmentApplication();

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getProcessingReport();

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getActiveReport();

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getInactiveReport();

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getAllApplicationOfCurrentEmployee(String employeeId);

    ResponseEntity<List<ApplicationDTO>> getAllApplication();

    ResponseEntity<Void> approveApplication(Integer id);

    ResponseEntity<Void> disapproveApplication(Integer id);

    ResponseEntity<Void> approveApplicationByHeadManager(Integer id);

    ResponseEntity<Void> disapproveApplicationByHeadManager(Integer id);
}
