package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.model.dto.ApplicationDTO;
import com.backend.swp.apalary.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    @PostMapping
    public ResponseEntity<Void> createApplication(@RequestBody ApplicationDTO applicationDTO, @RequestAttribute(required = false) String employeeId) {
        return applicationService.createApplication(applicationDTO, employeeId);
    }

    @GetMapping
    public ResponseEntity<List<ApplicationDTO>> getAllApplication() {
        return applicationService.getAllApplication();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDTO> getApplicationById(@PathVariable Integer id) {
        return applicationService.getApplicationById(id);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<Void> approveApplicationById(@PathVariable Integer id) {
        return applicationService.approveApplication(id);
    }

    @PutMapping("/disapprove/{id}")
    public ResponseEntity<Void> disapproveApplication(@PathVariable Integer id) {
        return applicationService.disapproveApplication(id);
    }
}
