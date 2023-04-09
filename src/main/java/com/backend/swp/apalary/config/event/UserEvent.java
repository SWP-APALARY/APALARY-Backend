package com.backend.swp.apalary.config.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@ToString
public class UserEvent extends ApplicationEvent {
    public UserEvent(Object source, String employeeName, String employeeEmail) {
        super(source);
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
    }

    private String employeeName;
    private String employeeEmail;
    private String username;
    private String password;

    public UserEvent(Object source, String employeeName, String employeeEmail, String username, String password) {
        super(source);
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.username = username;
        this.password = password;
    }
}
