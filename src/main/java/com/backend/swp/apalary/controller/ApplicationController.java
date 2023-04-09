package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.model.constant.Role;
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
    public ResponseEntity<List<ApplicationDTO>> getProcessingSalaryApplicationForHeadManager(@RequestAttribute(required = false) String userId, @RequestAttribute(required = false) Role userRole) {
        return applicationService.getProcessingSalaryIncreaseApplicationsForHeadManager(userId, userRole);
    }

    @Operation(summary = "Get all processing salary application for head manager")
    @GetMapping("/salary-increase/processing")
    public ResponseEntity<List<ApplicationDTO>> getProcessingSalaryApplication(@RequestAttribute(required = false) String userId, @RequestAttribute(required = false) Role userRole) {
        return applicationService.getProcessingSalaryIncreaseApplications(userId, userRole);
    }

    @Operation(summary = "Get all active salary application")
    @GetMapping("/salary-increase/active")
    public ResponseEntity<List<ApplicationDTO>> getActiveSalaryIncreaseApplication(@RequestAttribute(required = false) String userId, @RequestAttribute(required = false) Role userRole) {
        return applicationService.getActiveSalaryIncreaseApplication(userId, userRole);
    }

    @Operation(summary = "Get all inactive salary application")
    @GetMapping("/salary-increase/inactive")
    public ResponseEntity<List<ApplicationDTO>> getInactiveSalaryIncreaseApplication(@RequestAttribute(required = false) String userId, @RequestAttribute(required = false) Role userRole) {
        return applicationService.getInactiveSalaryIncreaseApplication(userId, userRole);
    }

    @Operation(summary = "Get processing day leave application")
    @GetMapping("/day-leave/processing")
    public ResponseEntity<List<ApplicationDTO>> getProcessingDayLeaveApplication(@RequestAttribute(required = false) String userId, @RequestAttribute(required = false) Role userRole) {
        return applicationService.getProcessingDayLeaveApplication(userId, userRole);
    }

    @Operation(summary = "Get all active day leave application")
    @GetMapping("/day-leave/active")
    public ResponseEntity<List<ApplicationDTO>> getActiveDayLeaveApplication(@RequestAttribute(required = false) String userId, @RequestAttribute(required = false) Role userRole) {
        return applicationService.getActiveDayLeaveApplication(userId, userRole);
    }

    @Operation(summary = "Get all inactive day leave application")
    @GetMapping("/day-leave/inactive")
    public ResponseEntity<List<ApplicationDTO>> getInactiveDayLeaveApplication(@RequestAttribute(required = false) String userId, @RequestAttribute(required = false) Role userRole) {
        return applicationService.getInactiveDayLeaveApplication(userId, userRole);
    }

    @Operation(summary = "Get all processing recruitment application")
    @GetMapping("/recruitment/processing")
    public ResponseEntity<List<ApplicationDTO>> getProcessingRecruitmentApplication(@RequestAttribute(required = false) String userId, @RequestAttribute(required = false) Role userRole) {
        return applicationService.getProcessingRecruitment(userId, userRole);
    }

    @Operation(summary = "Get all active recruitment application")
    @GetMapping("/recruitment/active")
    public ResponseEntity<List<ApplicationDTO>> getActiveRecruitmentApplication(@RequestAttribute(required = false) String userId, @RequestAttribute(required = false) Role userRole) {
        return applicationService.getActiveRecruitmentApplication(userId, userRole);
    }
    @Operation(summary = "Get all inactive recruitment application")
    @GetMapping("/recruitment/inactive")
    public ResponseEntity<List<ApplicationDTO>> getInactiveRecruitmentApplication(@RequestAttribute(required = false) String userId, @RequestAttribute(required = false) Role userRole) {
        return applicationService.getInactiveRecruitmentApplication(userId, userRole);
    }

    @Operation(summary = "Get all processing report application")
    @GetMapping("/report/processing")
    public ResponseEntity<List<ApplicationDTO>> getProcessingReportApplication(@RequestAttribute(required = false) String userId, @RequestAttribute(required = false) Role userRole) {
        return applicationService.getProcessingReport(userId, userRole);
    }

    @Operation(summary = "Get all active report application")
    @GetMapping("/report/active")
    public ResponseEntity<List<ApplicationDTO>> getActiveReportApplication(@RequestAttribute(required = false) String userId, @RequestAttribute(required = false) Role userRole) {
        return applicationService.getActiveReport(userId, userRole);
    }

    @Operation(summary = "Get all inactive report application")
    @GetMapping("/report/inactive")
    public ResponseEntity<List<ApplicationDTO>> getInactiveReportApplication(@RequestAttribute(required = false) String userId, @RequestAttribute(required = false) Role userRole) {
        return applicationService.getInactiveReport(userId, userRole);
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
