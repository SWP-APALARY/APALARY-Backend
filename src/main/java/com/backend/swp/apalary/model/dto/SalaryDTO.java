package com.backend.swp.apalary.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryDTO{
    private Integer id;
    private Integer month;
    private Integer year;
    private Integer net;
    private String description;
    private String contractId;
}
