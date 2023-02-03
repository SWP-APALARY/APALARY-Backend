package com.backend.swp.apalary.model.dto;

import com.backend.swp.apalary.model.constant.Role;
import lombok.Data;

import java.sql.Date;

@Data
public class EmployeeDTO {
    private String id;
    private String username;
    private String name;
    private Date dateOfBirth;
    private String identifyNumber;
    private String phone;
    private String email;
    private Role role;
    private String managerId;
    private String departmentId;
    private String contractId;
}
