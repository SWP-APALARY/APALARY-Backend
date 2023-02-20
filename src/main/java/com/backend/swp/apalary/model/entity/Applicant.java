package com.backend.swp.apalary.model.entity;

import com.backend.swp.apalary.model.constant.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "applicant")
@AllArgsConstructor
@NoArgsConstructor
public class Applicant {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String phone;
    @Column
    private String email;
    @Column
    @Lob
    private byte[] cv;
    @Column
    private int gender;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "job_offering_id")
    private JobOffering jobOffering;

}
