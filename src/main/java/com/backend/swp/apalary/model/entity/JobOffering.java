package com.backend.swp.apalary.model.entity;

import com.backend.swp.apalary.model.constant.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "job_offering")
@Data
public class JobOffering {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String title;
    @Column
    private String description;
    @Column(name = "base_salary")
    private Integer baseSalary;
    @Column(name = "created_time")
    private Date createdTime;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "max_employee")
    private Integer maxEmployee;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
