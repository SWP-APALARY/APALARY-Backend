package com.backend.swp.apalary.service;

import com.backend.swp.apalary.model.request.EmailDetails;

public interface EmailService {
    void sendHtmlEmail(EmailDetails detail);
}
