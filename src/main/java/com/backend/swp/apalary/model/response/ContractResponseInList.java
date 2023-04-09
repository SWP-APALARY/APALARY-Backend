package com.backend.swp.apalary.model.response;

import com.backend.swp.apalary.model.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractResponseInList{
    private String id;
    private String employeeId;
    private String nameEmp;
    private Integer base;
    private Integer tax;
    private Double socialAssurances;
    private Double medicalAssurances;
    private Double accidentalAssurances;
    private String description;
    private Date signedDate;
    private Date startDate;
    private Date endDate;
    private Integer numberOfDependents;
    private Status status;
    private String contractTypeType;
}
