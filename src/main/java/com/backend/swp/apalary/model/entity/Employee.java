package com.backend.swp.apalary.model.entity;

import com.backend.swp.apalary.model.constant.Role;
import com.backend.swp.apalary.model.constant.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee implements UserDetails {
    @Id
    @Column
    private String id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private int gender;
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
    @Enumerated(EnumType.STRING)
    @Column
    private Status status;
    @Column(name = "manager_id")
    private String managerId;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @OneToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<Application> createApplications;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<JobOffering> jobOfferings;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "destinationEmployee")
    private List<Application> mentionedApplication;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.name())) ;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
