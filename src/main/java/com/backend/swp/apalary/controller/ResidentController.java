package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.model.dto.ResidentDTO;
import com.backend.swp.apalary.service.ResidentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resident")
@RequiredArgsConstructor
public class ResidentController {
    private final ResidentService residentService;
    @Operation(summary = "Get all residents")
    @GetMapping("/all")
    public ResponseEntity<List<ResidentDTO>> getAllResident() {
        return residentService.getAllResident();
    }
    @Operation(summary = "Get resident by id")
    @GetMapping("/{id}")
    public ResponseEntity<ResidentDTO> getResidentById(@PathVariable String id) {
        return residentService.getResidentById(id);
    }
    @Operation(summary = "Get current resident")
    @GetMapping
    public ResponseEntity<ResidentDTO> getResident(@RequestAttribute(required = false) String userId) {
        return residentService.getResidentById(userId);
    }
    @Operation(summary = "Update current resident by providing dto")
    @PutMapping
    public ResponseEntity<Void> updateProfile(@RequestBody ResidentDTO residentDTO, @RequestAttribute(required = false) String userId) {
        return residentService.updateResident(residentDTO, userId);
    }
    @Operation(summary = "Ban resident")
    @DeleteMapping("/{residentId}")
    public ResponseEntity<Void> banResident(@PathVariable String residentId) {
        return residentService.banResident(residentId);
    }
}
