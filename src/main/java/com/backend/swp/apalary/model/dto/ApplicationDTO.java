package com.backend.swp.apalary.model.dto;

import com.backend.swp.apalary.model.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO {
    private String id;
    private String title;
    private String description;
    private Timestamp createdTime;
    private int presentDay;
    private int absentDay;
    private int otDay;
    private Status status;
    private Integer applicationTypeId;
    private String employeeId;
}
