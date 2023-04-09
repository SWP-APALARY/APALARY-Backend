package com.backend.swp.apalary.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class SalaryDetailMonthly {
    private int month;
    private Long total;
    private Long totalAssurance;
    private Long totalTax;
    private Long totalPenalty;
    private Long totalBonus;
}
