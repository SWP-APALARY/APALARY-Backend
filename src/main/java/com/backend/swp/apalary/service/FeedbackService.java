package com.backend.swp.apalary.service;

import com.backend.swp.apalary.config.exception.BadRequestException;
import com.backend.swp.apalary.model.dto.FeedbackDTO;
import com.backend.swp.apalary.model.response.FeedbackLinkResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FeedbackService {
    ResponseEntity<Void> createFeedBack(FeedbackDTO feedbackDTO) throws BadRequestException;

    ResponseEntity<List<FeedbackDTO>> getAllFeedbackOfAnEmployee(String employeeId);

    ResponseEntity<List<FeedbackDTO>> getAllFeedbackInMonthAndYear(Integer month, Integer year);


    ResponseEntity<List<FeedbackDTO>> getAllFeedbackOfAnEmployeeInSpecificMonth(String employeeId, Integer month, Integer year);

    ResponseEntity<List<FeedbackLinkResponse>> getAllLinkFeedBack();

    ResponseEntity<FeedbackLinkResponse> getFeedbackLinkOfAnEmployee(String employeeId) throws BadRequestException;
}
