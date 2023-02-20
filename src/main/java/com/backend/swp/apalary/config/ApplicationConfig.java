package com.backend.swp.apalary.config;

import com.backend.swp.apalary.model.constant.Status;
import com.backend.swp.apalary.model.entity.Employee;
import com.backend.swp.apalary.model.entity.Resident;
import com.backend.swp.apalary.repository.EmployeeRepository;
import com.backend.swp.apalary.repository.ResidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final EmployeeRepository employeeRepository;
    private final ResidentRepository residentRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Employee employee = employeeRepository.findEmployeeByUsernameAndStatus(username, Status.ACTIVE);
            if (employee != null) {
                return employee;
            }
            Resident resident = residentRepository.findResidentByUsernameAndStatus(username, Status.ACTIVE);
            if (resident != null) {
                return resident;
            }
            throw new UsernameNotFoundException("Username not found");
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
