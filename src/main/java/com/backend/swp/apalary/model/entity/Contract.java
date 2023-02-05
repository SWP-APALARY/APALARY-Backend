package com.backend.swp.apalary.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Date;

@Entity
@Table(name = "contract")
@Data
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
    @ManyToOne
    @JoinColumn(name = "contract_type_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ContractType contractType;
    @OneToOne(mappedBy = "contract")
    private Employee employee;
}
