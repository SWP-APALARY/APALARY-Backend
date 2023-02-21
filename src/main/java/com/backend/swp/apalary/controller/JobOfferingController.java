package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.model.dto.JobOfferingDTO;
import com.backend.swp.apalary.service.Impl.JobOfferingServiceImpl;
import com.backend.swp.apalary.service.JobOfferingService;
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
    @PutMapping
    public ResponseEntity<Void> updateJobOfferingById(@RequestBody JobOfferingDTO jobOfferingDTO) {
        return jobOfferingService.updateJobOffering(jobOfferingDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobOfferingById(@PathVariable Integer id) {
        return jobOfferingService.deleteJobOffering(id);
    }
}
