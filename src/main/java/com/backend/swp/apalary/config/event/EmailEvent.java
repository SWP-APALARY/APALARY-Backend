package com.backend.swp.apalary.config.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class EmailEvent extends ApplicationEvent {
    private boolean isAccepted;
    private String applicantName;
    private String applicantEmail;

    public EmailEvent(Object source, boolean isAccepted, String applicantName, String applicantEmail) {
        super(source);
        this.isAccepted = isAccepted;
        this.applicantName = applicantName;
        this.applicantEmail = applicantEmail;
    }
}
