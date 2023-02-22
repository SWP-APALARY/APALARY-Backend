package com.backend.swp.apalary.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "contract_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractType {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String type;
    @OneToMany(mappedBy = "contractType", fetch = FetchType.LAZY)
    private List<Contract> contracts;

}
