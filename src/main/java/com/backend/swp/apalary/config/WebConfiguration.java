package com.backend.swp.apalary.config;

import com.backend.swp.apalary.model.dto.ApplicantDTO;
import com.backend.swp.apalary.model.entity.Applicant;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
}
