package com.backend.swp.apalary.service.Impl;

import com.backend.swp.apalary.model.constant.Status;
import com.backend.swp.apalary.model.dto.SalaryDTO;
import com.backend.swp.apalary.model.entity.*;
import com.backend.swp.apalary.repository.*;
import com.backend.swp.apalary.service.SalaryService;
import com.backend.swp.apalary.service.constant.ServiceMessage;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {
    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;
    private final ContractRepository contractRepository;
    private final FeedbackRepository feedbackRepository;
    private final ApplicationRepository applicationRepository;
    private final ModelMapper modelMapper;

    private static final Logger logger = LogManager.getLogger(SalaryServiceImpl.class);
    private static final String GET_SALARY_MESSAGE = "Get salary: ";
    private static final String CREATE_SALARY_MESSAGE = "Create salary: ";
    private static final String UPDATE_SALARY_MESSAGE = "Update salary: ";

    @Override
    public ResponseEntity<List<SalaryDTO>> getSalaryByMonthAndYear(Integer month, Integer year) {
        logger.info("{}month: {}, year: {}", GET_SALARY_MESSAGE, month, year);
        if (month < 1 || month > 12) {
            logger.warn(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Salary> salaries = salaryRepository.findSalaryByMonthAndYear(month, year);
        List<SalaryDTO> salaryDTOS = salaries.stream().map(salary -> modelMapper.map(salary, SalaryDTO.class)).toList();
        logger.info("{} successful.", GET_SALARY_MESSAGE);
        return new ResponseEntity<>(salaryDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SalaryDTO>> getSalaryByEmployeeId(String employeeId) {
        logger.info("{}{}", GET_SALARY_MESSAGE, employeeId);
        if (employeeId == null) {
            logger.warn(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Employee employee = employeeRepository.findEmployeeByIdAndStatus(employeeId, Status.ACTIVE);
        if (employee == null) {
            logger.warn(ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Contract contract = employee.getContract();
        if (contract == null) {
            logger.warn(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Salary> salaries = salaryRepository.findSalaryByContract(contract);
        List<SalaryDTO> salaryDTOS = salaries.stream().map(salary -> modelMapper.map(salary, SalaryDTO.class)).toList();
        return new ResponseEntity<>(salaryDTOS, HttpStatus.OK);
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 3 4 * ?", zone = "Asia/Ho_Chi_Minh")
    public void calculateSalary() {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();
        if (month == 1) {
            month = 12;
        }
        else {
            month--;
        }
        if (month == 12) year--;
        final int finalMonth = month;
        final int finalYear = year;
        logger.info("Calculate salary for month : {} and year: {}", month, year);
        List<Application> applications = applicationRepository.findApplicationPreviousMonth();
        applications.forEach(application -> {
            Employee destinationEmployee = application.getDestinationEmployee();
            Contract contract = destinationEmployee.getContract();
            int point = 0;
            List<RuleSalary> ruleSalaries = contract.getRuleSalaries();
            Employee employee = employeeRepository.findEmployeeByContract(contract);
            for (RuleSalary ruleSalary: ruleSalaries) {
                point += calculatePointByRuleSalaryMonthly(application, employee, ruleSalary.getRuleNumber());
            }
            Salary salary = new Salary();
            salary.setId(null);
            salary.setMonth(finalMonth);
            salary.setYear(finalYear);
            int base = contract.getBase();
            double taxRate = ((double) contract.getTax()) / 100;
            double assurancesRate = ((double) contract.getAssurances()) / 100;
            int net = (int) (base * (1 + point * 0.001 - taxRate - assurancesRate));
            if (finalMonth == 2) {
                if (ruleSalaries.contains(new RuleSalary(6))) {
                    if (isExcellentEmployee(employee)) net += 0.1 * base;
                }
            }
            salary.setNet(net);
            salary.setContract(contract);
            salary.setDescription("description");
            logger.info("Calculate salary for employee {} successfully.", employee.getId());
            salaryRepository.save(salary);
        });
    }

    private int calculatePointByRuleSalaryMonthly(Application application, Employee employee, int ruleNumber) {
        if (ruleNumber == 1) {
            return application.getLateDay() * (-10);
        }
        if (ruleNumber == 2) {
            return Math.max(0, application.getAbsentDay() - 2) * (-100);
        }

        if (ruleNumber == 3) {
            Double averageStar = feedbackRepository.averageStarOfAnEmployee(employee.getId());
            if (averageStar == null) return 0;
            if (averageStar >= 3) return 0;
            return (int) Math.max(0, (3 - averageStar)) * (-250);
        }
        if (ruleNumber == 4) {
           return application.getOtDay() * 10;
        }
        if (ruleNumber == 5) {
            Double averageStar = feedbackRepository.averageStarOfAnEmployee(employee.getId());
            if (averageStar == null) return 0;
            if (averageStar < 4) return 0;
            Integer amountFeedback = feedbackRepository.countFeedbackOfAnEmployee(employee.getId());
            if (amountFeedback < 5) return 0;
            return (int) (averageStar * 20);
        }
        return 0;
    }

    boolean isExcellentEmployee(Employee employee) {
        Integer amountFeedback = feedbackRepository.countFeedbackOfAnEmployeeOfEntireYear(employee.getId());
        if (amountFeedback >= 50) {
            Double averageStar = feedbackRepository.averageStarOfAnEmployee(employee.getId());
            return averageStar >= 4.5;
        }
        return false;
    }

}
