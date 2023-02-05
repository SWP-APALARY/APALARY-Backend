package com.backend.swp.apalary.model.entity;

import com.backend.swp.apalary.model.constant.Status;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "job_offering")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(name = "created_time", insertable = false, updatable = false)
    private Timestamp createdTime;
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jobOffering")
    private List<Applicant> applicants;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobOffering that = (JobOffering) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
