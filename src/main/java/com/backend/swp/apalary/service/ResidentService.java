package com.backend.swp.apalary.service;

import com.backend.swp.apalary.model.dto.ResidentDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ResidentService {
    ResponseEntity<ResidentDTO> getResidentById(String id);

    ResponseEntity<List<ResidentDTO>> getAllResident();

    ResponseEntity<Void> updateResident(ResidentDTO residentDTO, String userId);


    ResponseEntity<Void> banResident(String residentId);
}
