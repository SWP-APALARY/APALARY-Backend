package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.model.dto.ApplicantDTO;
import com.backend.swp.apalary.model.response.ApplicantResponseInList;
import com.backend.swp.apalary.service.ApplicantService;
import com.backend.swp.apalary.service.Impl.ApplicantServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/applicant")
public class ApplicantController {
    final
    ApplicantService applicantService;

    public ApplicantController(ApplicantServiceImpl applicantService) {
        this.applicantService = applicantService;
    }

    @PostMapping
    public ResponseEntity<Void> createApplicant(@RequestBody ApplicantDTO applicantDTO){
        return applicantService.createApplicant(applicantDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApplicantDTO> getApplicantById(@PathVariable Integer id) {
        return applicantService.getApplicantById(id);
    }
    @GetMapping("/job-offering/{id}")
    public ResponseEntity<List<ApplicantResponseInList>> getApplicantOfAJobOffering(@PathVariable Integer id) {
        return applicantService.getAllApplicantOfAJobOffering(id);
    }
    @GetMapping("/processing")
    public ResponseEntity<List<ApplicantResponseInList>> getAllProcessingApplicant() {
        return applicantService.getAllProcessingApplicant();
    }
    @GetMapping("/accepted")
    public ResponseEntity<List<ApplicantResponseInList>> getAcceptedApplicant() {
        return applicantService.getAcceptedApplicant();
    }
    @GetMapping("/rejected")
    public ResponseEntity<List<ApplicantResponseInList>> getRejectedApplicant() {
        return applicantService.getRejectedApplicant();
    }
    @PutMapping("accept")
    public ResponseEntity<Void> acceptApplicant(@RequestParam Integer applicantId, @RequestParam boolean isAccepted){
        return applicantService.acceptApplicant(applicantId, isAccepted);
    }


}
