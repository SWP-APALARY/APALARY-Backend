package com.backend.swp.apalary.config;

import com.backend.swp.apalary.model.constant.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    private final HeaderSettingFilter headerSettingFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/job-offering/**").hasAnyRole(Role.HR_MANAGER.name(), Role.HR_EMPLOYEE.name())
                .requestMatchers(HttpMethod.GET, "/job-offering/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/job-offering").hasAnyRole(Role.HR_MANAGER.name(), Role.HR_EMPLOYEE.name())
                .requestMatchers(HttpMethod.DELETE, "/job-offering/**").hasAnyRole(Role.HR_MANAGER.name(), Role.HR_EMPLOYEE.name())
                .requestMatchers(HttpMethod.GET, "/employee/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/employee").authenticated()
                .requestMatchers(HttpMethod.DELETE, "employee/**").hasAnyRole(Role.HR_MANAGER.name(), Role.HR_EMPLOYEE.name())
                .requestMatchers(HttpMethod.POST, "/applicant/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/applicant/**").hasAnyRole(Role.HR_EMPLOYEE.name(), Role.HR_MANAGER.name(), Role.MANAGER.name(), Role.HEAD_MANAGER.name())
                .requestMatchers(HttpMethod.PUT, "/applicant/**").hasAnyRole(Role.HR_EMPLOYEE.name(), Role.HR_MANAGER.name())
                .requestMatchers(HttpMethod.POST, "/contract/**").hasAnyRole(Role.HR_EMPLOYEE.name(), Role.HR_MANAGER.name(), Role.HEAD_MANAGER.name())
                .requestMatchers(HttpMethod.GET, "/contract/all/**", "/contract/{id:[0-9A-Za-z]+}", "contract/rules-salary").hasAnyRole(Role.HR_EMPLOYEE.name(), Role.HR_MANAGER.name(), Role.MANAGER.name(), Role.HEAD_MANAGER.name())
                .requestMatchers(HttpMethod.GET, "/contract").authenticated()
                .requestMatchers(HttpMethod.GET, "/contract/unassigned").hasAnyRole(Role.HR_EMPLOYEE.name(), Role.HEAD_MANAGER.name())
                .requestMatchers(HttpMethod.DELETE, "/contract/**").hasAnyRole(Role.HR_EMPLOYEE.name(), Role.HR_MANAGER.name())
                .requestMatchers(HttpMethod.GET, "/department/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/application/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/application/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/application/**").hasAnyRole(Role.HR_MANAGER.name(), Role.HR_EMPLOYEE.name())
                .requestMatchers(HttpMethod.GET, "/application/salary-increase/**", "/application/day-leave/**", "/application/recruitment/**", "/application/report/**").hasAnyRole(Role.HR_EMPLOYEE.name(), Role.HR_MANAGER.name())
                .requestMatchers(HttpMethod.GET, "/application/salary-increase/processing-r2").hasRole(Role.HEAD_MANAGER.name())
                .requestMatchers(HttpMethod.PUT, "/application/approve/salary-increase/**", "/application/disapprove/salary-increase/**").hasRole(Role.HEAD_MANAGER.name())
                .requestMatchers(HttpMethod.GET,"/application-type/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/salary/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/resident").hasAnyRole(Role.RESIDENT.name())
                .requestMatchers(HttpMethod.GET, "/resident/**").hasAnyRole(Role.HR_EMPLOYEE.name(), Role.HR_MANAGER.name())
                .requestMatchers(HttpMethod.PUT, "/resident").hasRole(Role.RESIDENT.name())
                .requestMatchers(HttpMethod.DELETE, "/resident/**").hasAnyRole(Role.EMPLOYEE.name(), Role.HR_MANAGER.name(), Role.HR_EMPLOYEE.name(), Role.HEAD_MANAGER.name())
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(headerSettingFilter, CorsFilter.class);
        return http.build();

//        http.cors().and()
//                .csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/auth/**").permitAll()
//                .requestMatchers(HttpMethod.GET, "/job-offering/**").permitAll()
//                .requestMatchers(HttpMethod.POST, "/applicant/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(headerSettingFilter, JwtAuthenticationFilter.class);
//        return http.build();
      }
}
