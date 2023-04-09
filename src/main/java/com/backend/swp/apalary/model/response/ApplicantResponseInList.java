package com.backend.swp.apalary.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantResponseInList {
    private Integer id;
    private String name;
    private String phone;
    private String email;
    private int gender;
    private Date interviewDate;
    private String jobOfferingDepartmentName;
}
