package com.backend.swp.apalary.service;

import com.backend.swp.apalary.model.dto.JobOfferingDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface JobOfferingService {
    ResponseEntity<Void> createJobOffering(JobOfferingDTO jobOfferingDTO);
    ResponseEntity<List<JobOfferingDTO>> getAllJobOfferings();
    ResponseEntity<JobOfferingDTO> getJobOfferingById(Integer id);
    ResponseEntity<Void> updateJobOffering(JobOfferingDTO jobOfferingDTO);
    ResponseEntity<Void> deleteJobOffering(Integer id);
}
