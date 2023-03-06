package com.backend.swp.apalary.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {
    private String id;
    private Integer base;
    private Integer tax;
    private Double socialAssurances;
    private Double medicalAssurances;
    private Double accidentalAssurances;
    private String description;
    private Date signedDate;
    private Date startDate;
    private Date endDate;
    private String contractImage;
    private Integer contractTypeId;
    private int[] ruleSalaryRuleNumbers;
}
