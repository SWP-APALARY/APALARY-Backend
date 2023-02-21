package com.backend.swp.apalary.service;

import com.backend.swp.apalary.model.dto.ApplicantDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ApplicantService {
    ResponseEntity<Void> createApplicant(ApplicantDTO applicantDTO);
    ResponseEntity<List<ApplicantDTO>> getAllApplicant();
    ResponseEntity<List<ApplicantDTO>> getAllApplicantOfAJobOffering(Integer jobOfferingId);
    ResponseEntity<ApplicantDTO> getApplicantById(Integer id);
    ResponseEntity<Void> acceptApplicant(Integer id, boolean isAccepted);
}
