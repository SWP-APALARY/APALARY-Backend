package com.backend.swp.apalary.model.response;

import com.backend.swp.apalary.model.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseInList {
    private String id;
    private String username;
    private String name;
    private int gender;
    private Date dateOfBirth;
    private String identifyNumber;
    private String phone;
    private String email;
    private Role role;
    private String managerId;
    private String managerName;
    private String departmentId;
    private String departmentName;
    private String contractId;
}
