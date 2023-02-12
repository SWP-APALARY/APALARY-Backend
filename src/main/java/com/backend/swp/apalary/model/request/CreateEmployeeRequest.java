package com.backend.swp.apalary.model.request;

import com.backend.swp.apalary.model.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeRequest {
    private String id;
    private String username;
    private String password;
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
