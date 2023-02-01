package com.backend.swp.apalary.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "contract_type")
@Data
public class ContractType {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String type;

}
