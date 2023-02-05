package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.model.dto.ApplicantDTO;
import com.backend.swp.apalary.service.ApplicantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/applicant")
public class ApplicantController {
    final
    ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @PostMapping
    public ResponseEntity<Void> createApplicant(@RequestBody ApplicantDTO applicantDTO, @RequestParam("file")MultipartFile file) throws IOException {
        return applicantService.createApplicant(applicantDTO, file);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApplicantDTO> getApplicantById(@PathVariable Integer id) {
        return applicantService.getApplicantById(id);
    }
    @GetMapping("/job-offering/{id}")
    public ResponseEntity<List<ApplicantDTO>> getApplicantOfAJobOffering(@PathVariable Integer id) {
        return applicantService.getAllApplicantOfAJobOffering(id);
    }
    @GetMapping
    public ResponseEntity<List<ApplicantDTO>> getAllApplicant() {
        return applicantService.getAllApplicant();
    }
    @PutMapping("accept")
    public ResponseEntity<Void> acceptApplicant(@RequestParam Integer applicantId, @RequestParam boolean isAccepted){
        return applicantService.acceptApplicant(applicantId, isAccepted);
    }
}
