package com.backend.swp.apalary.service.Impl;

import com.backend.swp.apalary.config.exception.BadRequestException;
import com.backend.swp.apalary.model.constant.Status;
import com.backend.swp.apalary.model.dto.FeedbackDTO;
import com.backend.swp.apalary.model.entity.Employee;
import com.backend.swp.apalary.model.entity.Feedback;
import com.backend.swp.apalary.model.response.FeedbackLinkResponse;
import com.backend.swp.apalary.repository.EmployeeRepository;
import com.backend.swp.apalary.repository.FeedbackRepository;
import com.backend.swp.apalary.service.FeedbackService;
import com.backend.swp.apalary.service.constant.ServiceMessage;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private final static String CREATE_FEEDBACK_MESSAGE = "Create feedback: ";
    private final static String GET_FEEDBACK_MESSAGE = "Get feedback: ";
    private final static Logger logger = LogManager.getLogger(FeedbackServiceImpl.class);

    @Override
    public ResponseEntity<Void> createFeedBack(FeedbackDTO feedbackDTO) throws BadRequestException {
        logger.info("{}{}", CREATE_FEEDBACK_MESSAGE, feedbackDTO);
        Employee employee = employeeRepository.findEmployeeByIdAndStatus(feedbackDTO.getEmployeeId(), Status.ACTIVE);
        if (employee == null) {
            logger.info(ServiceMessage.ID_NOT_EXIST_MESSAGE.getMessage());
            throw new BadRequestException("Employee id is no longer exist.");
        }
        Feedback feedback = modelMapper.map(feedbackDTO, Feedback.class);
        feedback.setId(null);
        feedback.setEmployee(employee);
        feedbackRepository.save(feedback);
        logger.info("Create feedback for employee {} successfully.", feedbackDTO.getEmployeeId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Transactional
    @Override
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbackOfAnEmployee(String employeeId) {
        logger.info("{}{}", GET_FEEDBACK_MESSAGE, employeeId);
        List<Feedback> feedbacks = feedbackRepository.findFeedbackByEmployeeId(employeeId);
        List<FeedbackDTO> feedbackDTOS = feedbacks.stream().map(feedback -> modelMapper.map(feedback, FeedbackDTO.class)).toList();
        logger.info("Get all feedback of employee {} successfully.", employeeId);
        return new ResponseEntity<>(feedbackDTOS, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbackInMonthAndYear(Integer month, Integer year) {
        logger.info("{}month {} year {}", GET_FEEDBACK_MESSAGE, month, year);
        List<Feedback> feedbacks = feedbackRepository.findFeedbackByMonthAndYear(month, year);
        List<FeedbackDTO> feedbackDTOS = feedbacks.stream().map(feedback -> modelMapper.map(feedback, FeedbackDTO.class)).toList();
        logger.info("Get all feedback of month {} and year {} successfully.", month, year);
        return new ResponseEntity<>(feedbackDTOS, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbackOfAnEmployeeInSpecificMonth(String employeeId, Integer month, Integer year) {
        logger.info("{} employee {} in month {} and year {}",GET_FEEDBACK_MESSAGE, employeeId, month, year);
        List<Feedback> feedbacks = feedbackRepository.findFeedbackOfAnEmployeeByMonthAndYear(employeeId, month, year);
        List<FeedbackDTO> feedbackDTOS = feedbacks.stream().map(feedback -> modelMapper.map(feedback, FeedbackDTO.class)).toList();
        logger.info("Get all feedback of employee {} month {} and year {} successfully.",employeeId, month, year);
        return new ResponseEntity<>(feedbackDTOS, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<List<FeedbackLinkResponse>> getAllLinkFeedBack() {
        logger.info("Get link feedback of all employees");
        List<Employee> employees = employeeRepository.findEmployeeByStatus(Status.ACTIVE);
        List<FeedbackLinkResponse> feedbackLinkResponses = employees.stream().map(employee -> modelMapper.map(employee, FeedbackLinkResponse.class)).toList();
        logger.info("Get all link feedback successfully.");
        return new ResponseEntity<>(feedbackLinkResponses, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<FeedbackLinkResponse> getFeedbackLinkOfAnEmployee(String employeeId) throws BadRequestException {
        logger.info("Get link feedback of employee {}", employeeId);
        Employee employee = employeeRepository.findEmployeeByIdAndStatus(employeeId, Status.ACTIVE);
        if (employee == null) {
            logger.info(ServiceMessage.ID_NOT_EXIST_MESSAGE.getMessage());
            throw new BadRequestException("Employee is not exist.");
        }
        FeedbackLinkResponse feedbackLinkResponse = modelMapper.map(employee, FeedbackLinkResponse.class);
        logger.info("Get link feedback of employee {} successfully.", employeeId);
        return new ResponseEntity<>(feedbackLinkResponse, HttpStatus.OK);
    }
}
