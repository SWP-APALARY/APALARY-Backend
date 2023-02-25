package com.backend.swp.apalary.service;

import com.backend.swp.apalary.model.dto.ApplicationDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ApplicationService {

    ResponseEntity<Void> createApplication(ApplicationDTO applicationDTO, String employeeId);

    ResponseEntity<ApplicationDTO> getApplicationById(Integer id);

    ResponseEntity<List<ApplicationDTO>> getAllApplication();

    ResponseEntity<Void> approveApplication(Integer id);

    ResponseEntity<Void> disapproveApplication(Integer id);
}
