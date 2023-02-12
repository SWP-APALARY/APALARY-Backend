package com.backend.swp.apalary.model.entity;

import com.backend.swp.apalary.model.constant.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;

@Entity
@Data
@Table(name = "resident")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Resident implements UserDetails {
    @Id
    @Column
    private String id;
    @Column(unique = true)
    private String username;
    @Column
    private String password;
    @Column
    private String name;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "identify_number", unique = true)
    private String identifyNumber;
    @Column(unique = true)
    private String email;
    @Column
    private String phone;
    @Column(name = "apartment_number")
    private String apartmentNumber;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("RESIDENT")) ;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
