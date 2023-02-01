package com.backend.swp.apalary.model.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class ContractDTO {
    private String id;
    private Integer base;
    private Integer tax;
    private Integer assurances;
    private String description;
    private Date signedDate;
    private Date startDate;
    private Date endDate;
    private Integer contractTypeId;
}
