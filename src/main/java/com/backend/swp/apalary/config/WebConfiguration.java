package com.backend.swp.apalary.config;

import com.backend.swp.apalary.config.converter.RuleSalaryConverter;
import com.backend.swp.apalary.config.converter.RuleSalaryTimeConverter;
import com.backend.swp.apalary.model.dto.ContractDTO;
import com.backend.swp.apalary.model.dto.SalaryDTO;
import com.backend.swp.apalary.model.entity.Contract;
import com.backend.swp.apalary.model.entity.Salary;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Properties;

@Configuration

public class WebConfiguration implements WebMvcConfigurer {
    @Value("${spring.mail.host}")
    private String mailServerHost;
    @Value("${spring.mail.port}")
    private Integer mailServerPort;
    @Value("${spring.mail.username}")
    private String sender;
    @Value("${spring.mail.password}")
    private String password;
    @Bean
    @Primary
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean(name = "contractMapper")
    public ModelMapper contractMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Contract, ContractDTO> propertyMap = modelMapper.createTypeMap(Contract.class, ContractDTO.class);
        propertyMap.addMappings(mapper -> mapper.using(new RuleSalaryConverter()).map(Contract::getRuleSalaries, ContractDTO::setRuleSalaryDescription));
        return modelMapper;
    }
    @Bean(name = "salaryMapper")
    public ModelMapper salaryMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Salary, SalaryDTO> propertyMap = modelMapper.createTypeMap(Salary.class, SalaryDTO.class);
        propertyMap.addMappings(mapper -> mapper.using(new RuleSalaryTimeConverter()).map(Salary::getTimes, SalaryDTO::setRuleSalaryObtain));
        propertyMap.addMappings(mapper -> mapper.map(Salary::getTotal, SalaryDTO::setTotal));
        return modelMapper;
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://be.duybinhngo.live", "http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "HEAD", "OPTIONS", "PATCH"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailServerHost);
        mailSender.setPort(mailServerPort);

        mailSender.setUsername(sender);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }

    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(8);
        threadPoolTaskExecutor.setMaxPoolSize(16);
        threadPoolTaskExecutor.initialize();
        eventMulticaster.setTaskExecutor(threadPoolTaskExecutor);
        return eventMulticaster;
    }
}
