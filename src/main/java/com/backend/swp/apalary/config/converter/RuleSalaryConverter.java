package com.backend.swp.apalary.config.converter;

import com.backend.swp.apalary.model.entity.RuleSalary;
import org.modelmapper.AbstractConverter;

import java.util.List;

public class RuleSalaryConverter extends AbstractConverter<List<RuleSalary>, String[]> {
    @Override
    protected String[] convert(List<RuleSalary> ruleSalaries) {
        String[] result = new String[ruleSalaries.size()];
        ruleSalaries.stream().map(RuleSalary::getDescription).toList().toArray(result);
        return result;
    }
}
