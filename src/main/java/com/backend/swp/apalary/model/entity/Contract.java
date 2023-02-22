package com.backend.swp.apalary.model.entity;

import com.backend.swp.apalary.model.constant.Status;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "contract")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Contract {
    @Column
    @Id
    private String id;
    @Column
    private Integer base;
    @Column
    private Integer tax;
    @Column
    private Integer assurances;
    @Column
    private String description;
    @Column(name = "signed_date")
    private Date signedDate;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "contract_image")
    private String contractImage;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "contract_type_id")
    private ContractType contractType;
    @OneToOne(mappedBy = "contract")
    private Employee employee;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "contract_has_rule_salary",
            joinColumns = @JoinColumn(name = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "rule_salary_rule_number")
    )
    List<RuleSalary> ruleSalaries;
}
