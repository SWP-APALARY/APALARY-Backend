package com.backend.swp.apalary.model.entity;

import com.backend.swp.apalary.model.constant.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Date;

@Entity
@Table(name = "employee")
@Data
public class Employee {
    @Id
    @Column
    private String id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String name;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column
    private String identifyNumber;
    @Column
    private String phone;
    @Column
    private String email;
    @Enumerated(EnumType.STRING)
    @Column
    private Role role;
    @Column(nullable = true, name = "manager_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String managerId;
    @ManyToOne
    @JoinColumn(name = "department_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Department department;


}
