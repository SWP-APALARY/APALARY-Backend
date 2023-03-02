package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.model.dto.ApplicationTypeDTO;
import com.backend.swp.apalary.service.ApplicationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/application-type")
@RequiredArgsConstructor
public class ApplicationTypeController {
    private final ApplicationTypeService applicationTypeService;

    @GetMapping()
    public ResponseEntity<List<ApplicationTypeDTO>> getAllApplicationType() {
        return applicationTypeService.getAllApplicationType();
    }
}
