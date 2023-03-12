package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.model.dto.ApplicationDTO;
import com.backend.swp.apalary.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    @Operation(summary = "Create Application by providing DTO")
    @PostMapping
    public ResponseEntity<Void> createApplication(@RequestBody ApplicationDTO applicationDTO, @RequestAttribute(required = false) String userId) {
        return applicationService.createApplication(applicationDTO, userId);
    }
    @Operation(summary = "Get all application")
    @GetMapping
    public ResponseEntity<List<ApplicationDTO>> getAllApplication() {
        return applicationService.getAllApplication();
    }

    @Operation(summary = "Get all processing salary application for head manager")
    @GetMapping("/salary-increase/processing-r2")
    public ResponseEntity<List<ApplicationDTO>> getProcessingSalaryApplicationForHeadManager() {
        return applicationService.getProcessingSalaryIncreaseApplicationsForHeadManager();
    }

    @Operation(summary = "Get all processing salary application for head manager")
    @GetMapping("/salary-increase/processing")
    public ResponseEntity<List<ApplicationDTO>> getProcessingSalaryApplication() {
        return applicationService.getProcessingSalaryIncreaseApplications();
    }

    @Operation(summary = "Get all active salary application")
    @GetMapping("/salary-increase/active")
    public ResponseEntity<List<ApplicationDTO>> getActiveSalaryIncreaseApplication() {
        return applicationService.getActiveSalaryIncreaseApplication();
    }

    @Operation(summary = "Get all inactive salary application")
    @GetMapping("/salary-increase/inactive")
    public ResponseEntity<List<ApplicationDTO>> getInactiveSalaryIncreaseApplication() {
        return applicationService.getInactiveSalaryIncreaseApplication();
    }

    @Operation(summary = "Get processing day leave application")
    @GetMapping("/day-leave/processing")
    public ResponseEntity<List<ApplicationDTO>> getProcessingDayLeaveApplication() {
        return applicationService.getProcessingDayLeaveApplication();
    }

    @Operation(summary = "Get all active day leave application")
    @GetMapping("/day-leave/active")
    public ResponseEntity<List<ApplicationDTO>> getActiveDayLeaveApplication() {
        return applicationService.getActiveDayLeaveApplication();
    }

    @Operation(summary = "Get all inactive day leave application")
    @GetMapping("/day-leave/inactive")
    public ResponseEntity<List<ApplicationDTO>> getInactiveDayLeaveApplication() {
        return applicationService.getInactiveDayLeaveApplication();
    }

    @Operation(summary = "Get all processing recruitment application")
    @GetMapping("/recruitment/processing")
    public ResponseEntity<List<ApplicationDTO>> getProcessingRecruitmentApplication() {
        return applicationService.getProcessingRecruitment();
    }

    @Operation(summary = "Get all active recruitment application")
    @GetMapping("/recruitment/active")
    public ResponseEntity<List<ApplicationDTO>> getActiveRecruitmentApplication() {
        return applicationService.getActiveRecruitmentApplication();
    }
    @Operation(summary = "Get all inactive recruitment application")
    @GetMapping("/recruitment/inactive")
    public ResponseEntity<List<ApplicationDTO>> getInactiveRecruitmentApplication() {
        return applicationService.getInactiveRecruitmentApplication();
    }

    @Operation(summary = "Get all processing report application")
    @GetMapping("/report/processing")
    public ResponseEntity<List<ApplicationDTO>> getProcessingReportApplication() {
        return applicationService.getProcessingReport();
    }

    @Operation(summary = "Get all active report application")
    @GetMapping("/report/active")
    public ResponseEntity<List<ApplicationDTO>> getActiveReportApplication() {
        return applicationService.getActiveReport();
    }

    @Operation(summary = "Get all inactive report application")
    @GetMapping("/report/inactive")
    public ResponseEntity<List<ApplicationDTO>> getInactiveReportApplication() {
        return applicationService.getInactiveReport();
    }

    @Operation(summary = "Get all application of current employee")
    @GetMapping("/employee")
    public ResponseEntity<List<ApplicationDTO>> getAllApplicationOfCurrentEmployee(@RequestAttribute(required = false) String userId) {
        return applicationService.getAllApplicationOfCurrentEmployee(userId);
    }
    @Operation(summary = "Get application by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDTO> getApplicationById(@PathVariable Integer id) {
        return applicationService.getApplicationById(id);
    }

    @Operation(summary = "Approve application by id")
    @PutMapping("/approve/{id}")
    public ResponseEntity<Void> approveApplicationById(@PathVariable Integer id) {
        return applicationService.approveApplication(id);
    }

    @Operation(summary = "Disapprove application by id")
    @PutMapping("/disapprove/{id}")
    public ResponseEntity<Void> disapproveApplication(@PathVariable Integer id) {
        return applicationService.disapproveApplication(id);
    }

    @Operation(summary = "Head manager Approve application by id")
    @PutMapping("/approve/salary-increase/{id}")
    public ResponseEntity<Void> approveApplicationByHeadManager(@PathVariable Integer id) {
        return applicationService.approveApplicationByHeadManager(id);
    }

    @Operation(summary = "Head manager disapprove application by id")
    @PutMapping("/disapprove/salary-increase/{id}")
    public ResponseEntity<Void> disapproveApplicationByHeadManager(@PathVariable Integer id) {
        return applicationService.disapproveApplicationByHeadManager(id);
    }

}
