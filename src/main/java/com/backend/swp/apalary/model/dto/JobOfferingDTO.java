package com.backend.swp.apalary.model.dto;

import com.backend.swp.apalary.model.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobOfferingDTO {
    private Integer id;
    private String title;
    private String description;
    private Integer baseSalary;
    private Timestamp createdTime;
    private Status status;
    private Integer maxEmployee;
    private Integer departmentId;
    private String departmentName;
    private String employeeId;
}
