package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.model.dto.JobOfferingDTO;
import com.backend.swp.apalary.service.JobOfferingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job-offering")
public class JobOfferingController {
    final JobOfferingService jobOfferingService;

    public JobOfferingController(JobOfferingService jobOfferingService) {
        this.jobOfferingService = jobOfferingService;
    }

    @PostMapping
    public ResponseEntity<Void> createJobOffering(@RequestBody JobOfferingDTO jobOfferingDTO) {
        return jobOfferingService.createJobOffering(jobOfferingDTO);
    }
    @GetMapping
    public ResponseEntity<List<JobOfferingDTO>> getAllJobOfferings() {
        return jobOfferingService.getAllJobOfferings();
    }
    @GetMapping("/{id}")
    public ResponseEntity<JobOfferingDTO> getJobOfferingById(@PathVariable Integer id) {
        return jobOfferingService.getJobOfferingById(id);
    }
    @PutMapping("/approve/all")
    public ResponseEntity<Void> approveAll() {
        return jobOfferingService.approveAll();
    }
    @PutMapping("/approve/{id}")
    public ResponseEntity<Void> approveJobOfferingById(@PathVariable Integer id) {
        return jobOfferingService.approveJobOffering(id);
    }
    @PutMapping("/disapprove/all")
    public ResponseEntity<Void> disapproveAll() {
        return jobOfferingService.disApproveAll();
    }
    @PutMapping("/disapprove/{id}")
    public ResponseEntity<Void> disApproveJobOfferingById(@PathVariable Integer id) {
        return jobOfferingService.disApproveJobOffering(id);
    }
    @PutMapping
    public ResponseEntity<Void> updateJobOfferingById(@RequestBody JobOfferingDTO jobOfferingDTO) {
        return jobOfferingService.updateJobOffering(jobOfferingDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobOfferingById(@PathVariable Integer id) {
        return jobOfferingService.deleteJobOffering(id);
    }
}
