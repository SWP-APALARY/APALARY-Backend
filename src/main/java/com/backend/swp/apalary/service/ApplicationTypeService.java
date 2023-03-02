package com.backend.swp.apalary.service;

import com.backend.swp.apalary.model.dto.ApplicationTypeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ApplicationTypeService {
    @Transactional
    ResponseEntity<List<ApplicationTypeDTO>> getAllApplicationType();
}
