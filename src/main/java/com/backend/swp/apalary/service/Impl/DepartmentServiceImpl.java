package com.backend.swp.apalary.service.Impl;

import com.backend.swp.apalary.model.dto.DepartmentDTO;
import com.backend.swp.apalary.model.entity.Department;
import com.backend.swp.apalary.repository.DepartmentRepository;
import com.backend.swp.apalary.service.DepartmentService;
import com.backend.swp.apalary.service.constant.ServiceMessage;
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
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LogManager.getLogger(DepartmentDTO.class);

    @Override
    @Transactional
    public ResponseEntity<List<DepartmentDTO>> getAllDepartment() {
        return new ResponseEntity<>(departmentRepository.findAll().stream().map(department -> modelMapper.map(department, DepartmentDTO.class)).toList(), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<DepartmentDTO> getDepartmentByName(String name) {
        logger.info("Get Department name: {}", name);
        if (name == null) {
            logger.info(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Department department = departmentRepository.findDepartmentByName(name);
        if (department == null) {
            logger.info(ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
        logger.info("Get department name: {} successfully.", name);
        return new ResponseEntity<>(departmentDTO, HttpStatus.OK);
    }

}
