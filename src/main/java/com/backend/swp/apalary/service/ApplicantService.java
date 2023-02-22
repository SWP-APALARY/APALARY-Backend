package com.backend.swp.apalary.service;

import com.backend.swp.apalary.model.dto.ApplicantDTO;
import com.backend.swp.apalary.model.response.ApplicantResponseInList;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ApplicantService {
    ResponseEntity<Void> createApplicant(ApplicantDTO applicantDTO);
    ResponseEntity<List<ApplicantResponseInList>> getAllProcessingApplicant();
    ResponseEntity<List<ApplicantResponseInList>> getAllApplicantOfAJobOffering(Integer jobOfferingId);
    ResponseEntity<ApplicantDTO> getApplicantById(Integer id);
    ResponseEntity<List<ApplicantResponseInList>> getAcceptedApplicant();
    ResponseEntity<List<ApplicantResponseInList>> getRejectedApplicant();
    ResponseEntity<Void> acceptApplicant(Integer id, boolean isAccepted);
}
