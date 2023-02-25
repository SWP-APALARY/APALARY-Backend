package com.backend.swp.apalary.model.entity;

import com.backend.swp.apalary.model.constant.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "application")
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String title;
    @Column
    private String description;
    @Column(name = "created_time", insertable = false, updatable = false)
    private Timestamp createdTime;
    @Column(name = "present_day")
    private Integer presentDay;
    @Column(name = "absent_day")
    private Integer absentDay;
    @Column(name = "ot_day")
    private Integer otDay;
    private Status status;
    @ManyToOne
    @JoinColumn(name = "application_type_id")
    private ApplicationType applicationType;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
