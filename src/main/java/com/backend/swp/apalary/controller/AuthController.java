package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.config.exception.IdExistException;
import com.backend.swp.apalary.model.request.CreateEmployeeRequest;
import com.backend.swp.apalary.model.request.CreateResidentRequest;
import com.backend.swp.apalary.model.request.LoginRequest;
import com.backend.swp.apalary.model.response.AuthResponse;
import com.backend.swp.apalary.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @Operation(summary = "Create employee")
    @PostMapping("/create/employee")
    public ResponseEntity<AuthResponse> createEmployee(@RequestBody CreateEmployeeRequest request) throws IdExistException {
        return authService.createEmployee(request);
    }
    @Operation(summary = "Create resident")
    @PostMapping("/create/resident")
    public ResponseEntity<AuthResponse> createResident(@RequestBody CreateResidentRequest request) throws IdExistException {
        return authService.createResident(request);
    }
    @Operation(summary = "Login for both employee and resident")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        System.out.println(request);
        return authService.login(request);
    }

}
