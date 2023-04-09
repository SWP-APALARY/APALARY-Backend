package com.backend.swp.apalary.model.dto;


import com.backend.swp.apalary.model.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantDTO {
    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String cv;
    private int gender;
    private Date interviewDate;
    private Status status;
    private Integer jobOfferingId;
    private String jobOfferingDepartmentName;
}
