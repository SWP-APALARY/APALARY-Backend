package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.config.exception.BadRequestException;
import com.backend.swp.apalary.model.dto.FeedbackDTO;
import com.backend.swp.apalary.model.response.FeedbackLinkResponse;
import com.backend.swp.apalary.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {
    final FeedbackService feedbackService;
    @PostMapping
    @Operation(summary = "Create feedback for an employee")
    public ResponseEntity<Void> createFeedback(@RequestBody FeedbackDTO feedbackDTO) throws BadRequestException {
        return feedbackService.createFeedBack(feedbackDTO);
    }

    @GetMapping("/{employeeId}")
    @Operation(summary = "Get all feedback of an employee")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbackOfAnEmployee(@PathVariable String employeeId) {
        return feedbackService.getAllFeedbackOfAnEmployee(employeeId);
    }

    @GetMapping
    @Operation(summary = "Get all feedback in a month and year")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbackInMonthAndYear(@RequestParam Integer month, @RequestParam Integer year) {
        return feedbackService.getAllFeedbackInMonthAndYear(month, year);
    }

    @GetMapping("/time/employee")
    @Operation(summary = "Get all feedback of an employee in a specific month")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbackOfAnEmployeeInSpecificMonth(@RequestParam String employeeId, @RequestParam Integer month, @RequestParam Integer year) {
        return feedbackService.getAllFeedbackOfAnEmployeeInSpecificMonth(employeeId, month, year);
    }

    @GetMapping("/link")
    @Operation(summary = "Get all feedback link")
    public ResponseEntity<List<FeedbackLinkResponse>> getAllFeedbackLink() {
        return feedbackService.getAllLinkFeedBack();
    }

    @GetMapping("/link/{employeeId}")
    @Operation(summary = "Get all feedback of an employee in a specific month")
    public ResponseEntity<FeedbackLinkResponse> getAllFeedbackLinkOfAnEmployee(@PathVariable String employeeId) throws BadRequestException {
        return feedbackService.getFeedbackLinkOfAnEmployee(employeeId);
    }
}
