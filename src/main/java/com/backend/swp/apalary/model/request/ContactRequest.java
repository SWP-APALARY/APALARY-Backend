package com.backend.swp.apalary.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactRequest {
    private String name;
    private String email;
    private String subject;
    private String message;
}
