package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.model.dto.ApplicantDTO;
import com.backend.swp.apalary.model.request.ReasonDTO;
import com.backend.swp.apalary.model.response.ApplicantResponseInList;
import com.backend.swp.apalary.service.ApplicantService;
import com.backend.swp.apalary.service.Impl.ApplicantServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.criteria.CriteriaBuilder;
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

    @Operation(summary = "Create application with DTO")
    @PostMapping
    public ResponseEntity<Void> createApplicant(@RequestBody ApplicantDTO applicantDTO){
        return applicantService.createApplicant(applicantDTO);
    }
    @Operation(summary = "Get applicant by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApplicantDTO> getApplicantById(@PathVariable Integer id) {
        return applicantService.getApplicantById(id);
    }
    @Operation(summary = "Get all applicant of a job offering by job offering id")
    @GetMapping("/job-offering/{id}")
    public ResponseEntity<List<ApplicantResponseInList>> getApplicantOfAJobOffering(@PathVariable Integer id) {
        return applicantService.getAllApplicantOfAJobOffering(id);
    }
    @Operation(summary = "Get all processing applicant")
    @GetMapping("/processing")
    public ResponseEntity<List<ApplicantResponseInList>> getAllProcessingApplicant() {
        return applicantService.getAllProcessingApplicant();
    }
    @Operation(summary = "Get all accepted applicant")
    @GetMapping("/accepted")
    public ResponseEntity<List<ApplicantResponseInList>> getAcceptedApplicant() {
        return applicantService.getAcceptedApplicant();
    }
    @Operation(summary = "Get all rejected applicant")
    @GetMapping("/rejected")
    public ResponseEntity<List<ApplicantResponseInList>> getRejectedApplicant() {
        return applicantService.getRejectedApplicant();
    }

    @Operation(summary = "Get all applicant which passed cv round")
    @GetMapping("/approved")
    public ResponseEntity<List<ApplicantResponseInList>> getAllApplicantPassCV() {
        return applicantService.getApplicantPassCV();
    }
    @Operation(summary = "Accept an applicant based on is accepted variable with applicant id")
    @PutMapping("/accept")
    public ResponseEntity<Void> acceptApplicant(@RequestParam Integer applicantId, @RequestParam boolean isAccepted){
        return applicantService.acceptApplicant(applicantId);
    }
    @Operation(summary = "Approve cv base on isapproved variable with applicant id")
    @PutMapping("/approve")
    public ResponseEntity<Void> approveApplicant(@RequestParam Integer applicantId, @RequestParam boolean isApproved) {
        return applicantService.approveApplicantCVRound(applicantId);
    }

    @Operation(summary = "Reject an applicant")
    @PutMapping("/reject")
    public ResponseEntity<Void> rejectApplicant(@RequestParam Integer applicantId, @RequestBody ReasonDTO reasonDTO) {
        return applicantService.rejectApplicant(applicantId, reasonDTO.getReason());
    }

}
