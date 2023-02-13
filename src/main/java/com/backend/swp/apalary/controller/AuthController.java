package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.model.request.CreateEmployeeRequest;
import com.backend.swp.apalary.model.request.CreateResidentRequest;
import com.backend.swp.apalary.model.request.LoginRequest;
import com.backend.swp.apalary.service.AuthService;
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
    @PostMapping("/create/employee")
    public ResponseEntity<String> createEmployee(@RequestBody CreateEmployeeRequest request) {
        return authService.createEmployee(request);
    }
    @PostMapping("/create/resident")
    public ResponseEntity<String> createResident(@RequestBody CreateResidentRequest request) {
        return authService.createResident(request);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        System.out.println(request);
        return authService.login(request);
    }

}
