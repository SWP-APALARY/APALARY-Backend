package com.backend.swp.apalary.config.event;

import com.backend.swp.apalary.model.request.EmailDetails;
import com.backend.swp.apalary.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
public class EmailEventListener {
    private final EmailService emailService;
    @EventListener(EmailEvent.class)
    public void emailToApplicant(EmailEvent emailEvent) {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(emailEvent.getApplicantEmail());
        emailDetails.setSubject("Your application status at APALARY Apartment");
        if (emailEvent.isAccepted()) {
            Instant instant = Instant.now();
            ZoneId vietnamZoneId = ZoneId.of("Asia/Ho_Chi_Minh");
            ZonedDateTime dateInVietnam = ZonedDateTime.ofInstant(instant, vietnamZoneId);
            ZonedDateTime dateAfterAWeek = dateInVietnam.plusWeeks(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            emailDetails.setMsgBody("Dear " + emailEvent.getApplicantName() + ",<br><br>" +
                    "Thanks for your application with us. We have evaluated your resume and found your background and qualifications impressive. Hence, we would like to invite you for a further discussion regarding the job role and your fitment for it.<br><br>" +
                    "If you could appear for interview at APALARY Apartment we will get a chance to get to know you better while being able to tell you a bit more about position.<br><br>" +
                    "Here are our suggested time date for the interview. Please let me know which works the best for you. Please let me know which works the best for you, and we will send a confirmation email with other detail: <br>" +
                    dateAfterAWeek.format(formatter)+ "<br>" +
                    dateAfterAWeek.plusDays(1).format(formatter) + "<br>" +
                    dateAfterAWeek.plusDays(3).format(formatter) + "<br>" +
                    "Thanks again for your interest in joining APALARY! We're looking forward to speaking to you.<br><br>" +
                    "Best regards, <br>" +
                    "APALARY Apartment");
        }
        else {
            emailDetails.setMsgBody("Dear " + emailEvent.getApplicantName() +  ",<br><br>" +
                    "I would like to thank you for taking the time and effort to apply at APALARY. We appreciate your interest in joining our team.<br><br>" +
                    "After careful consideration, we regret to inform you that we have decided not to move forward with your application at this time. Please note that this decision was not made lightly, and we have thoroughly reviewed your application and qualifications.<br><br>" +
                    "We received a high volume of applications for this position, and unfortunately, we cannot offer a role to every applicant. While your skills and experience are impressive, we have decided to move forward with other candidates whose qualifications more closely align with our current needs.<br><br>" +
                    "We encourage you to continue pursuing your career goals and wish you all the best in your future endeavors. We will keep your resume in our database, and we may contact you if a suitable position becomes available in the future.<br><br>" +
                    "Thank you again for your interest in our company and for the time and effort you put into your application. We appreciate your understanding and wish you success in your job search.<br><br>" +
                    "Best regards, <br>" +
                    "APALARY Apartment");
        }
        emailService.sendHtmlEmail(emailDetails);
    }
}
