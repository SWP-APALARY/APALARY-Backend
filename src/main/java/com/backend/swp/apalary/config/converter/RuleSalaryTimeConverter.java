package com.backend.swp.apalary.config.converter;

import com.backend.swp.apalary.model.entity.RuleSalaryTime;
import org.modelmapper.AbstractConverter;

import java.util.List;

public class RuleSalaryTimeConverter extends AbstractConverter<List<RuleSalaryTime>, List<RuleSalaryObtain>> {

    @Override
    protected List<RuleSalaryObtain> convert(List<RuleSalaryTime> ruleSalaryTimes) {
        return ruleSalaryTimes.stream().map(ruleSalaryTime -> new RuleSalaryObtain(ruleSalaryTime.getRuleSalary().getDescription(), ruleSalaryTime.getTime(), ruleSalaryTime.getMoney())).toList();
    }
}
