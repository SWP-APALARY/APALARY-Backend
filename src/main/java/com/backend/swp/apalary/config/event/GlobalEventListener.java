package com.backend.swp.apalary.config.event;

import com.backend.swp.apalary.model.entity.Applicant;
import com.backend.swp.apalary.model.request.EmailDetails;
import com.backend.swp.apalary.repository.ApplicantRepository;
import com.backend.swp.apalary.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
public class GlobalEventListener {
    private final EmailService emailService;
    private final ApplicantRepository applicantRepository;
    @EventListener(EmailEvent.class)
    public void emailToApplicant(EmailEvent emailEvent) {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(emailEvent.getApplicantEmail());
        emailDetails.setSubject("Your application status at APALARY Apartment");
        if (emailEvent.getStatus() == 1) {
            Instant instant = Instant.now();
            ZoneId vietnamZoneId = ZoneId.of("Asia/Ho_Chi_Minh");
            int min = 5;
            int max = 9;
            int randomNumber = (int)Math.floor(Math.random() * (max - min + 1) + min);
            ZonedDateTime dateInVietnam = ZonedDateTime.ofInstant(instant, vietnamZoneId);
            ZonedDateTime dateAfterAWeek = dateInVietnam.plusDays(randomNumber);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            emailDetails.setMsgBody("Dear " + emailEvent.getApplicantName() + ",<br><br>" +
                    "Thanks for your application with us. We have evaluated your resume and found your background and qualifications impressive. Hence, we would like to invite you for a further discussion regarding the job role and your fitment for it.<br><br>" +
                    "If you could appear for interview at APALARY Apartment we will get a chance to get to know you better while being able to tell you a bit more about position.<br><br>" +
                    "Here are our date for the interview: <br>       " +
                    dateAfterAWeek.format(formatter)+ "<br><br>" +
                    "Thanks again for your interest in joining APALARY! We're looking forward to speaking to you.<br><br>" +
                    "Best regards, <br>" +
                    "APALARY Apartment");
            Applicant applicant = applicantRepository.findApplicantById(emailEvent.getApplicantId());
            applicant.setInterviewDate(Date.valueOf(dateAfterAWeek.toLocalDate()));
            applicantRepository.save(applicant);
        }
        else if (emailEvent.getStatus() == 0){
            emailDetails.setMsgBody("Dear " + emailEvent.getApplicantName() + ",<br><br>" +
                    "Thank you for taking the time to apply at APALARY. We appreciate your interest in joining our team and the effort you put into your application.<br><br>" +
                    "After careful consideration of your qualifications and experience, we regret to inform you that we have decided not to move forward with your candidacy. While we were impressed with your skills and experience, we have identified some specific areas where we feel your skills and experience are not a good match for the position: <br><br>" +
                    emailEvent.getReason() + "<br><br>" +
                    "We understand that this may be disappointing news, but we encourage you to continue to pursue other opportunities that align with your qualifications and experience. We appreciate your interest in our company and wish you the best in your future endeavors.<br><br>" +
                    "Thank you again for your application.<br><br>" +
                    "Best regards, <br>" +
                    "APALARY Apartment");
        } else {
            emailDetails.setMsgBody("Dear " + emailEvent.getApplicantName() + ",<br><br>" +
                    "<br>" +
                    "Thank you for taking the time to interview at APALARY. We enjoyed getting to know you. We have completed all of our interviews.<br>" +
                    "<br>" +
                    "I am pleased to inform you that we would like to offer you to be a part of us. We believe your past experience will be an asset to our company.<br>" +
                    "<br>" +
                    "Please respond to this email to let us know if you would like to accept our offer.<br>" +
                    "<br>" +
                    "I look forward to hearing from you.<br>" +
                    "<br>" +
                    "Best regards, <br>" +
                    "APALARY Apartment");
        }
        emailService.sendHtmlEmail(emailDetails);
    }
    @EventListener(UserEvent.class)
    public void emailToEmployee(UserEvent userEvent) {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(userEvent.getEmployeeEmail());
        if (userEvent.getUsername() != null) {
            emailDetails.setSubject("Important: APALARY Account Creation for " + userEvent.getEmployeeName());
            emailDetails.setMsgBody("Dear " + userEvent.getEmployeeName() + ",<br><br>" +
                    "I am pleased to inform you that we have created an account for you as a new employee of APALARY. Your account credentials are as follows:<br><br>" +
                    "Username: " + userEvent.getUsername() + "<br>" +
                    "Temporary Password: " + userEvent.getPassword() + "<br><br>" +
                    "Please log in to your account using the temporary password and update your password as soon as possible for security purposes. The account will give you access to [list of systems or applications the employee will have access to].<br><br>" +
                    "If you encounter any issues while logging in or have any questions about your account, please do not hesitate to contact us. We will be happy to assist you.<br><br>" +
                    "Welcome to the team, and we look forward to your contributions at APALARY.<br><br>" +
                    "Best regards,<br>" +
                    "APALARY apartment");
        } else {
            Instant instant = Instant.now();
            ZoneId vietnamZoneId = ZoneId.of("Asia/Ho_Chi_Minh");
            ZonedDateTime dateInVietnam = ZonedDateTime.ofInstant(instant, vietnamZoneId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm");
            emailDetails.setSubject("Confirmation of Password Change");
            emailDetails.setMsgBody("Dear " + userEvent.getEmployeeName() + ",<br><br>" +
                    "I am writing to confirm that your password has been successfully changed on your account. This change was initiated at " + dateInVietnam.format(formatter)  + ".<br><br>" +
                    "We take the security and privacy of your account very seriously, and we recommend that you do not share your password with anyone. If you suspect any unauthorized access to your account, please change your password immediately and contact our customer support team for assistance.<br><br>" +
                    "<br><br>" +
                    "Please feel free to reach out to us if you have any further questions or concerns.\n" +
                    "<br><br>" +
                    "Best regards,<br>" +
                    "APALARY apartment");
        }
        emailService.sendHtmlEmail(emailDetails);
    }

}
