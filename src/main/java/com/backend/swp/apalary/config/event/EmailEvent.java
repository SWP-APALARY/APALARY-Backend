package com.backend.swp.apalary.config.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
@ToString
public class EmailEvent extends ApplicationEvent {
    private int status;
    private Integer applicantId;
    private String applicantName;
    private String applicantEmail;

    private String reason;

    public EmailEvent(Object source, int status, Integer applicantId, String applicantName, String applicantEmail, String reason) {
        super(source);
        this.status = status;
        this.applicantId = applicantId;
        this.applicantName = applicantName;
        this.applicantEmail = applicantEmail;
        this.reason = reason;
    }

    public EmailEvent(Object source, int status, Integer applicantId, String applicantName, String applicantEmail) {
        super(source);
        this.status = status;
        this.applicantId = applicantId;
        this.applicantName = applicantName;
        this.applicantEmail = applicantEmail;
    }


}
