package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.model.dto.JobOfferingDTO;
import com.backend.swp.apalary.service.Impl.JobOfferingServiceImpl;
import com.backend.swp.apalary.service.JobOfferingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job-offering")
public class JobOfferingController {
    final JobOfferingService jobOfferingService;

    public JobOfferingController(JobOfferingServiceImpl jobOfferingService) {
        this.jobOfferingService = jobOfferingService;
    }

    @Operation(summary = "Create job offering by providing dto")
    @PostMapping
    public ResponseEntity<Void> createJobOffering(@RequestBody JobOfferingDTO jobOfferingDTO, @RequestAttribute(required = false) String userId) {
        return jobOfferingService.createJobOffering(jobOfferingDTO, userId);
    }
    @Operation(summary = "Get all job offerings")
    @GetMapping
    public ResponseEntity<List<JobOfferingDTO>> getAllJobOfferings() {
        return jobOfferingService.getAllJobOfferings();
    }
    @Operation(summary = "Get job offering by id")
    @GetMapping("/{id}")
    public ResponseEntity<JobOfferingDTO> getJobOfferingById(@PathVariable Integer id) {
        return jobOfferingService.getJobOfferingById(id);
    }
    @Operation(summary = "Update job offering by providing dto")
    @PutMapping
    public ResponseEntity<Void> updateJobOfferingById(@RequestBody JobOfferingDTO jobOfferingDTO) {
        return jobOfferingService.updateJobOffering(jobOfferingDTO);
    }
    @Operation(summary = "Delete job offering by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobOfferingById(@PathVariable Integer id) {
        return jobOfferingService.deleteJobOffering(id);
    }
}
