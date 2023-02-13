package com.backend.swp.apalary.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateResidentRequest {
    private String id;
    private String username;
    private String password;
    private String name;
    private Date dateOfBirth;
    private String identifyNumber;
    private String email;
    private String apartmentNumber;
}
