package com.backend.swp.apalary.service.Impl;

import com.backend.swp.apalary.model.request.EmailDetails;
import com.backend.swp.apalary.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String sender;
    private final JavaMailSender javaMailSender;
    private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);
    private static final String SEND_HTML_EMAIL = "Send html email: ";
    @Override
    public void sendHtmlEmail(EmailDetails detail) {
        logger.info("{}{}", SEND_HTML_EMAIL, detail);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try{
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            mimeMessage.setContent(detail.getMsgBody(), "text/html; charset=utf-8");
            mimeMessage.setHeader("Content-Type", "text/html; charset=UTF-8");
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(detail.getRecipient());
            mimeMessageHelper.setSubject(detail.getSubject());

            javaMailSender.send(mimeMessage);
            logger.info("{}{}", SEND_HTML_EMAIL, "Mail sent Successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
