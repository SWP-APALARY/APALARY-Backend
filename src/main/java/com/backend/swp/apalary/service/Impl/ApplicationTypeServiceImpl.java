package com.backend.swp.apalary.service.Impl;

import com.backend.swp.apalary.model.dto.ApplicationTypeDTO;
import com.backend.swp.apalary.model.entity.ApplicationType;
import com.backend.swp.apalary.repository.ApplicationTypeRepository;
import com.backend.swp.apalary.service.ApplicationTypeService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationTypeServiceImpl implements ApplicationTypeService {
    private final ApplicationTypeRepository applicationTypeRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LogManager.getLogger(ApplicationTypeServiceImpl.class);
    private static final String GET_APPLICATION_TYPE = "Get application type: ";
    @Transactional
    @Override
    public ResponseEntity<List<ApplicationTypeDTO>> getAllApplicationType() {
        logger.info("{}{}", GET_APPLICATION_TYPE, "all");
        List<ApplicationType> applicationTypes = applicationTypeRepository.findAll();
        List<ApplicationTypeDTO> applicationTypeDTOS = applicationTypes.stream().map(applicationType -> modelMapper.map(applicationType, ApplicationTypeDTO.class)).toList();
        logger.info("Get all application type successfully.");
        return new ResponseEntity<>(applicationTypeDTOS, HttpStatus.OK);
    }
}
