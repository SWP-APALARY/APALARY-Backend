package com.backend.swp.apalary.model.dto;

import com.backend.swp.apalary.model.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentDTO {
    private String id;
    private String username;
    private String name;
    private Date dateOfBirth;
    private String identifyNumber;
    private String email;
    private String phone;
    private String apartmentNumber;
    private Status status;
}
