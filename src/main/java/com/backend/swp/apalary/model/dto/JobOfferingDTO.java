package com.backend.swp.apalary.model.dto;

import com.backend.swp.apalary.model.constant.Status;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class JobOfferingDTO {
    private Integer id;
    private String title;
    private String description;
    private Integer baseSalary;
    private Timestamp createdTime;
    private Status status;
    private Integer maxEmployee;
    private Integer departmentId;
    private String employeeId;
}
