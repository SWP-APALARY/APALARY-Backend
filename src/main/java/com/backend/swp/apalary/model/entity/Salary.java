package com.backend.swp.apalary.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "salary")
@AllArgsConstructor
@NoArgsConstructor
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    private Integer month;
    @Column
    private Integer year;
    @Column
    private Integer net;
    @Column
    private Integer bonus;
    @Column
    private Integer penalty;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @OneToMany(mappedBy = "salary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RuleSalaryTime> times;
    public Integer getTotal() {
        return net + bonus - penalty;
    }
    public List<RuleSalary> getRuleSalary() {
        return times.stream().map(RuleSalaryTime::getRuleSalary).toList();
    }
}
