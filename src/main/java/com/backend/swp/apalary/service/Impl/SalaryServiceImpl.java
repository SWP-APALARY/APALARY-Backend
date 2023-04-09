package com.backend.swp.apalary.service.Impl;

import com.backend.swp.apalary.model.constant.Status;
import com.backend.swp.apalary.model.dto.EmployeeDTO;
import com.backend.swp.apalary.model.dto.SalaryDTO;
import com.backend.swp.apalary.model.entity.*;
import com.backend.swp.apalary.model.response.SalaryDetailMonthly;
import com.backend.swp.apalary.repository.*;
import com.backend.swp.apalary.service.SalaryService;
import com.backend.swp.apalary.service.constant.ServiceMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService {
    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;
    private final FeedbackRepository feedbackRepository;
    private final ApplicationRepository applicationRepository;
    private final ModelMapper modelMapper;

    private static final Logger logger = LogManager.getLogger(SalaryServiceImpl.class);

    private static final String GET_SALARY_MESSAGE = "Get salary: ";

    public SalaryServiceImpl(SalaryRepository salaryRepository, EmployeeRepository employeeRepository, FeedbackRepository feedbackRepository, ApplicationRepository applicationRepository, @Qualifier("salaryMapper") ModelMapper modelMapper) {
        this.salaryRepository = salaryRepository;
        this.employeeRepository = employeeRepository;
        this.feedbackRepository = feedbackRepository;
        this.applicationRepository = applicationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<List<SalaryDTO>> getSalaryByMonthAndYear(Integer month, Integer year) {
        logger.info("{}month: {}, year: {}", GET_SALARY_MESSAGE, month, year);
        if (month < 1 || month > 12) {
            logger.warn(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Salary> salaries = salaryRepository.findSalaryByMonthAndYear(month, year);
        List<SalaryDTO> salaryDTOS = salaries.stream().map(salary -> {
            SalaryDTO salaryDTO = modelMapper.map(salary, SalaryDTO.class);
            Contract contract = salary.getEmployee().getContract();
            salaryDTO.setTax(contract.getTax());
            salaryDTO.setAccidentalAssurance((int) (1.0 * 0.01 * contract.getBase()));
            salaryDTO.setMedicalAssurance((int) (1.5 * 0.01 * contract.getBase()));
            salaryDTO.setSocialAssurance((int) (8.0 * 0.01 * contract.getBase()));
            return salaryDTO;
        }).toList();
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
        List<Salary> salaries = salaryRepository.findSalaryByEmployee(employee);
        List<SalaryDTO> salaryDTOS = salaries.stream().map(salary -> {
            SalaryDTO salaryDTO = modelMapper.map(salary, SalaryDTO.class);
            Contract contract = salary.getEmployee().getContract();
            salaryDTO.setTax(contract.getTax());
            salaryDTO.setAccidentalAssurance((int) (1.0 * 0.01 * contract.getBase()));
            salaryDTO.setMedicalAssurance((int) (1.5 * 0.01 * contract.getBase()));
            salaryDTO.setSocialAssurance((int) (8.0 * 0.01 * contract.getBase()));
            return salaryDTO;
        }).toList();
        return new ResponseEntity<>(salaryDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SalaryDTO> getSalaryOfAnEmployeeByMonthAndYear(String employeeId, int month, int year) {
        logger.info("{}{}in month {} year {}", GET_SALARY_MESSAGE, employeeId, month, year);
        if (month < 1 || month > 12) {
            logger.warn(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!salaryRepository.existsByEmployeeId(employeeId)) {
            logger.warn(ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Salary salary = salaryRepository.findSalaryByEmployeeIdAndMonthAndYear(employeeId, month, year);
        if (salary == null) {
            logger.warn(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SalaryDTO salaryDTO = modelMapper.map(salary, SalaryDTO.class);
        Contract contract = salary.getEmployee().getContract();
        salaryDTO.setTax(contract.getTax());
        salaryDTO.setAccidentalAssurance((int) (1.0 * 0.01 * contract.getBase()));
        salaryDTO.setMedicalAssurance((int) (1.5 * 0.01 * contract.getBase()));
        salaryDTO.setSocialAssurance((int) (8.0 * 0.01 * contract.getBase()));
        logger.info("Get salary of employee {} in month {} and year {} successfully.", employeeId, month, year);
        return new ResponseEntity<>(salaryDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SalaryDTO> getSalaryOfAnEmployeeBySalaryId(Integer id) {
        logger.info("{}{}", GET_SALARY_MESSAGE, id);
        if (id == null) {
            logger.warn(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Salary salary = salaryRepository.findSalaryById(id);
        if (salary == null) {
            logger.warn(ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        SalaryDTO salaryDTO = modelMapper.map(salary, SalaryDTO.class);
        Contract contract = salary.getEmployee().getContract();
        salaryDTO.setTax(contract.getTax());
        salaryDTO.setAccidentalAssurance((int) (1.0 * 0.01 * contract.getBase()));
        salaryDTO.setMedicalAssurance((int) (1.5 * 0.01 * contract.getBase()));
        salaryDTO.setSocialAssurance((int) (8.0 * 0.01 * contract.getBase()));
        logger.info("Get salary by id {} successfully.", id);
        return new ResponseEntity<>(salaryDTO, HttpStatus.OK);
    }


    @Override
    @Transactional
    @Scheduled(cron = "0 0 3 2 * ?", zone = "Asia/Ho_Chi_Minh")
   //
    public void systemCalculateSalaryMonthly() {
        calculateSalary();
    }

    @Override
    @Transactional
    public ResponseEntity<Void> calculateSalaryManually() {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();
        if (month == 1) {
            month = 12;
        } else {
            month--;
        }
        if (month == 12) year--;
        List<Salary> salaries = salaryRepository.findSalaryByMonthAndYear(month, year);
        salaryRepository.deleteAll(salaries);
        calculateSalary();
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Transactional
    void calculateSalary() {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();
        if (month == 1) {
            month = 12;
        } else {
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
            int bonus = 0;
            int penalty = 0;
            List<RuleSalary> ruleSalaries = contract.getRuleSalaries();
            Employee employee = employeeRepository.findEmployeeByContract(contract);
            int base = contract.getBase();
            Salary salary = new Salary();
            salary.setTimes(new ArrayList<>());
            for (RuleSalary ruleSalary : ruleSalaries) {
                int point = calculatePointByRuleSalaryMonthly(application, employee, ruleSalary.getRuleNumber());
                if (point == 0)
                    continue;
                RuleSalaryTime ruleSalaryTime = new RuleSalaryTime();
                ruleSalaryTime.setSalary(salary);
                ruleSalaryTime.setRuleSalary(ruleSalary);
                ruleSalaryTime.setTime(getRuleSalaryObtainedTime(ruleSalary.getRuleNumber(), point));
                int money = (int) (base * 0.001 * point);
                ruleSalaryTime.setMoney(money);
                salary.getTimes().add(ruleSalaryTime);
                if (point > 0) bonus += point;
                else penalty -= point;
            }

            salary.setId(null);
            salary.setMonth(finalMonth);
            salary.setYear(finalYear);

            int net = base - contract.getTax() - contract.getAssurances();
            bonus = (int) (bonus * 0.001 * base);
            penalty = (int) (penalty * 0.001 * base);
            salary.setNet(net);
            salary.setBonus(bonus);
            salary.setPenalty(penalty);
            salary.setEmployee(employee);
            salary.setDescription("description");
            logger.info("Calculate salary for employee {} successfully.", employee.getId());
            salaryRepository.save(salary);
        });
    }

    @Override
    @Transactional
    public ResponseEntity<List<EmployeeDTO>> getEmployeeNotHaveSalaryInPreviousMonth() {
        logger.info("Get all employees weren't calculated salary in the previous month");
        List<Employee> employees = employeeRepository.findEmployeeHaveNoSalaryPreviousMonth();
        List<EmployeeDTO> employeeDTOS = employees.stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class)).toList();
        logger.info("Get all employees weren't calculated salary in the previous month successfully.");
        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);
    }
    @Override
    @Transactional
    public ResponseEntity<List<SalaryDetailMonthly>> getSalaryDetailsOfAYear(int year) {
        logger.info("Get salary detail of year {}", year);
        Instant instant = Instant.now();
        ZoneId vietnamZoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime dateInVietnam = ZonedDateTime.ofInstant(instant, vietnamZoneId);
        List<SalaryDetailMonthly> salaryDetails = new ArrayList<>();
        if (dateInVietnam.getYear() == year) {
            for (int i = 1; i < dateInVietnam.getMonth().getValue(); i++) {
                salaryDetails.add(getSalaryDetailMonthly(i, year));
            }
        }
        else {
            for (int i = 1; i <= 12; i++) {
                salaryDetails.add(getSalaryDetailMonthly(i, year));
            }
        }
        logger.info("Get salary detail year {} successfully.", year);
        return new ResponseEntity<>(salaryDetails, HttpStatus.OK);
    }
    private SalaryDetailMonthly getSalaryDetailMonthly(int month, int year) {

        Long totalBonus = salaryRepository.sumBonusInMonthAndYear(month, year);
        if (totalBonus == null) totalBonus = 0L;
        Long totalPenalty = salaryRepository.sumPenaltyInMonthAndYear(month, year);
        if (totalPenalty == null) totalPenalty = 0L;
        Long totalNet = salaryRepository.sumNetInMonthAndYear(month, year);
        if (totalNet == null) totalNet = 0L;
        Long totalMoney = totalNet  + totalBonus - totalPenalty;
        List<Salary> salaries = salaryRepository.findSalaryByMonthAndYear(month, year);
        Long totalTax = 0L;
        Long totalAssurance = 0L;
        for (Salary salary: salaries) {
            totalAssurance += salary.getEmployee().getContract().getAssurances();
            totalTax += salary.getEmployee().getContract().getTax();
        }
        SalaryDetailMonthly salaryDetail = new SalaryDetailMonthly(month, totalMoney, totalAssurance, totalTax, totalPenalty, totalBonus);

        return salaryDetail;
    }
    private int calculatePointByRuleSalaryMonthly(Application application, Employee employee, int ruleNumber) {
        if (ruleNumber == 1) {
            return application.getLateDay() * (-10);
        }
        if (ruleNumber == 2) {
            return Math.max(0, application.getAbsentDay() - 2) * (-100);
        }
        if (ruleNumber == 4) {
            return application.getOtDay() * 10;
        }
        return 0;
    }

    private boolean isExcellentEmployee(Employee employee) {
        Integer amountFeedback = feedbackRepository.countFeedbackOfAnEmployeeOfEntireYear(employee.getId());
        if (amountFeedback >= 50) {
            Double averageStar = feedbackRepository.averageStarOfAnEmployee(employee.getId());
            return averageStar >= 4.5;
        }
        return false;
    }


    private int getRuleSalaryObtainedTime(int ruleNumber, int point) {
        if (ruleNumber == 1)
            return point / (-10);
        if (ruleNumber == 2)
            return point / (-100);
        if (ruleNumber == 4)
            return point / 10;
        return 1;
    }
}
