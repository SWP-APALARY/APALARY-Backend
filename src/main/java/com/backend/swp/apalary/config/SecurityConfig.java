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
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/job-offering/**").hasAnyRole(Role.MANAGER.name(), Role.HR_MANAGER.name())
                .requestMatchers(HttpMethod.GET, "/job-offering/**").permitAll()
                .requestMatchers("/job-offering/approve/**").hasRole(Role.HR_MANAGER.name())
                .requestMatchers("/job-offering/disapprove/**").hasRole(Role.HR_MANAGER.name())
                .requestMatchers(HttpMethod.PUT, "/job-offering").hasRole(Role.MANAGER.name())
                .requestMatchers(HttpMethod.DELETE, "/job-offering/**").hasAnyRole(Role.MANAGER.name(), Role.HR_MANAGER.name())
                .requestMatchers(HttpMethod.GET, "/employee/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/employee").authenticated()
                .requestMatchers(HttpMethod.POST, "/applicant/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/applicant/**").hasAnyRole(Role.HR_EMPLOYEE.name(), Role.HR_MANAGER.name(), Role.MANAGER.name())
                .requestMatchers(HttpMethod.PUT, "/applicant/**").hasAnyRole(Role.HR_EMPLOYEE.name(), Role.HR_MANAGER.name())
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(headerSettingFilter, JwtAuthenticationFilter.class)    ;
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
