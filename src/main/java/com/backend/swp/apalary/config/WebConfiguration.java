package com.backend.swp.apalary.config;

import com.backend.swp.apalary.model.dto.ApplicantDTO;
import com.backend.swp.apalary.model.dto.ContractDTO;
import com.backend.swp.apalary.model.entity.Applicant;
import com.backend.swp.apalary.model.entity.Contract;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration

public class WebConfiguration implements WebMvcConfigurer {
    @Bean
    @Primary
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean(name = "applicant")
    public ModelMapper applicantMapper() {
        ModelMapper mapper = new ModelMapper();
        TypeMap<Applicant, ApplicantDTO> propertyMap = mapper.createTypeMap(Applicant.class, ApplicantDTO.class);
        propertyMap.addMappings(mp -> mp.skip(ApplicantDTO::setCv));
        TypeMap<ApplicantDTO, Applicant> anotherPropertyMap = mapper.createTypeMap(ApplicantDTO.class, Applicant.class);
        anotherPropertyMap.addMappings(mp -> mp.skip(Applicant::setCv));
        return mapper;
    }
    @Bean(name = "contract")
    public ModelMapper contractMapper() {
        ModelMapper mapper = new ModelMapper();
        TypeMap<ContractDTO, Contract> propertyMap = mapper.createTypeMap(ContractDTO.class, Contract.class);
        propertyMap.addMappings(mp -> mp.skip(Contract::setRuleSalaries));
        propertyMap.addMappings(mp -> mp.skip(Contract::setContractImage));
        TypeMap<Contract, ContractDTO> anotherPropertyMap = mapper.createTypeMap(Contract.class, ContractDTO.class);
        anotherPropertyMap.addMappings(mp -> mp.skip(ContractDTO::setContractImage));
        return mapper;
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://be.binhdt.dev", "http://be.binhdt.dev", "http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "HEAD", "OPTIONS", "PATCH"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
