package com.backend.swp.apalary.service;

import com.backend.swp.apalary.config.exception.BadRequestException;
import com.backend.swp.apalary.config.exception.IdExistException;
import com.backend.swp.apalary.model.request.ChangePasswordRequest;
import com.backend.swp.apalary.model.request.CreateEmployeeRequest;
import com.backend.swp.apalary.model.request.CreateResidentRequest;
import com.backend.swp.apalary.model.request.LoginRequest;
import com.backend.swp.apalary.model.response.AuthResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<AuthResponse> createEmployee(CreateEmployeeRequest employeeDTO) throws BadRequestException;

    ResponseEntity<AuthResponse> changeEmployeePassword(ChangePasswordRequest changePasswordRequest, String userId) throws BadRequestException;

    ResponseEntity<AuthResponse> createResident(CreateResidentRequest residentDTO) throws BadRequestException;
    ResponseEntity<AuthResponse> login(LoginRequest userLogin);
}
