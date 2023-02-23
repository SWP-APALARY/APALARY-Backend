package com.backend.swp.apalary.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "contract_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationType {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String type;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "applicationType")
    private List<Application> applications;
}
