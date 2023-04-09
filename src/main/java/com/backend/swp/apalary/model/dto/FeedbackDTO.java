package com.backend.swp.apalary.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
    private Integer id;
    private String title;
    private String description;
    private Integer star;
    private Date createdDate;
    private String employeeId;
}
