package com.backend.swp.apalary.service;

import com.backend.swp.apalary.model.constant.Role;
import com.backend.swp.apalary.model.dto.ApplicationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ApplicationService {

    ResponseEntity<Void> createApplication(ApplicationDTO applicationDTO, String employeeId);

    ResponseEntity<ApplicationDTO> getApplicationById(Integer id);

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getProcessingSalaryIncreaseApplicationsForHeadManager(String userId, Role role);

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getProcessingSalaryIncreaseApplications(String userId, Role role);

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getActiveSalaryIncreaseApplication(String userId, Role role);

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getInactiveSalaryIncreaseApplication(String userId, Role role);

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getProcessingDayLeaveApplication(String userId, Role role);

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getActiveDayLeaveApplication(String userId, Role role);

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getInactiveDayLeaveApplication(String userId, Role role);

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getProcessingRecruitment(String userId, Role role);

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getActiveRecruitmentApplication(String userId, Role role);

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getInactiveRecruitmentApplication(String userId, Role role);

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getProcessingReport(String userId, Role role);

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getActiveReport(String userId, Role role);

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getInactiveReport(String userId, Role role);

    @Transactional
    ResponseEntity<List<ApplicationDTO>> getAllApplicationOfCurrentEmployee(String employeeId);

    ResponseEntity<List<ApplicationDTO>> getAllApplication();

    ResponseEntity<Void> approveApplication(Integer id);

    ResponseEntity<Void> disapproveApplication(Integer id);

    ResponseEntity<Void> approveApplicationByHeadManager(Integer id);

    ResponseEntity<Void> disapproveApplicationByHeadManager(Integer id);
}
