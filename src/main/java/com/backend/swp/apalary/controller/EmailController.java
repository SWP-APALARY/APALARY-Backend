package com.backend.swp.apalary.controller;

import com.backend.swp.apalary.model.request.ContactRequest;
import com.backend.swp.apalary.model.request.EmailDetails;
import com.backend.swp.apalary.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send")
    public void sendEmail(@RequestBody ContactRequest contactRequest) {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient("apalary.apartment@gmail.com");
        emailDetails.setSubject(contactRequest.getSubject());
        emailDetails.setMsgBody("We have receive a request to contact from " + contactRequest.getName() + " with email: " + contactRequest.getEmail() + "<br><br>" +
                "He/She send with message: <br><br>" +
                contactRequest.getMessage() + "<br><br>" +
                "Please take time to contact to this candidate to make sure that everything is clear.<br><br>" +
                "Best regards,<br>" +
                "APALARY Apartment");
        emailService.sendHtmlEmail(emailDetails);
    }
}
