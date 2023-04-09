package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.config.exception.BadRequestException;
import com.backend.swp.apalary.config.exception.IdExistException;
import com.backend.swp.apalary.model.request.ChangePasswordRequest;
import com.backend.swp.apalary.model.request.CreateEmployeeRequest;
import com.backend.swp.apalary.model.request.CreateResidentRequest;
import com.backend.swp.apalary.model.request.LoginRequest;
import com.backend.swp.apalary.model.response.AuthResponse;
import com.backend.swp.apalary.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @Operation(summary = "Create employee")
    @PostMapping("/create/employee")
    public ResponseEntity<AuthResponse> createEmployee(@RequestBody CreateEmployeeRequest request) throws BadRequestException {
        return authService.createEmployee(request);
    }
    @Operation(summary = "Create resident")
    @PostMapping("/create/resident")
    public ResponseEntity<AuthResponse> createResident(@RequestBody CreateResidentRequest request) throws BadRequestException {
        return authService.createResident(request);
    }
    @Operation(summary = "Login for both employee and resident")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
    @Operation(summary = "Self-change password")
    @PostMapping("/change-password")
    public ResponseEntity<AuthResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, @RequestAttribute String userId) throws BadRequestException {
        return authService.changeEmployeePassword(changePasswordRequest, userId);
    }

}
