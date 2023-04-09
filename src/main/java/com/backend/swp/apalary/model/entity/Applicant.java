package com.backend.swp.apalary.model.entity;

import com.backend.swp.apalary.model.constant.Status;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "applicant")
@AllArgsConstructor
@NoArgsConstructor
public class Applicant {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String phone;
    @Column
    private String email;
    @Column
    @Basic(fetch = FetchType.LAZY)
    private String cv;
    @Column
    private int gender;
    @Column(name = "interview_date")
    private Date interviewDate;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "job_offering_id")
    private JobOffering jobOffering;

}
