package com.backend.swp.apalary.model.dto;

import com.backend.swp.apalary.config.converter.RuleSalaryObtain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryDTO{
    private Integer id;
    private Integer month;
    private Integer year;
    private Integer net;
    private Integer bonus;
    private Integer penalty;
    private Integer total;
    private String description;
    private String contractId;
    private List<RuleSalaryObtain> ruleSalaryObtain;
}
