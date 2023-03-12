package com.backend.swp.apalary.service.Impl;

import com.backend.swp.apalary.model.constant.Status;
import com.backend.swp.apalary.model.dto.ResidentDTO;
import com.backend.swp.apalary.model.entity.Resident;
import com.backend.swp.apalary.repository.ResidentRepository;
import com.backend.swp.apalary.service.ResidentService;
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
public class ResidentServiceImpl implements ResidentService{
    private final ResidentRepository residentRepository;
    private final ModelMapper modelMapper;
    private final static String GET_RESIDENT_MESSAGE = "Get resident: ";
    private final static String UPDATE_RESIDENT_MESSAGE = "Update resident: ";
    private final static Logger logger = LogManager.getLogger();
    @Override
    public ResponseEntity<ResidentDTO> getResidentById(String id) {
        logger.info("{}{}", GET_RESIDENT_MESSAGE, id);
        if (id == null) {
            logger.warn("{}" , ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Resident resident = residentRepository.findResidentByIdAndStatus(id, Status.ACTIVE);
        if (resident == null) {
            logger.warn("{}", ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ResidentDTO residentDTO = modelMapper.map(resident, ResidentDTO.class);
        logger.info("Get employee id {} successfully.", id);
        return new ResponseEntity<>(residentDTO, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<List<ResidentDTO>> getAllResident() {
        logger.info("{}{}", GET_RESIDENT_MESSAGE, "all");
        List<Resident> residents = residentRepository.findResidentByStatus(Status.ACTIVE);
        List<ResidentDTO> residentDTOS = residents.stream().map(resident -> modelMapper.map(resident, ResidentDTO.class)).toList();
        logger.info("Get all employee successfully.");
        return new ResponseEntity<>(residentDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateResident(ResidentDTO residentDTO, String userId) {
        logger.info("{}{}", UPDATE_RESIDENT_MESSAGE, residentDTO);
        if (residentDTO == null) {
            logger.warn("{}" , ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Resident resident = residentRepository.findResidentByIdAndStatus(userId, Status.ACTIVE);
        if (resident == null) {
            logger.warn("{}", ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (residentDTO.getName() != null) {
            resident.setName(residentDTO.getName());
        }
        if (residentDTO.getDateOfBirth() != null) {
            resident.setDateOfBirth(residentDTO.getDateOfBirth());
        }
        if (residentDTO.getIdentifyNumber() != null) {
            resident.setIdentifyNumber(residentDTO.getIdentifyNumber());
        }
        if (residentDTO.getPhone() != null) {
            resident.setPhone(residentDTO.getPhone());
        }
        if (residentDTO.getEmail() != null) {
            resident.setEmail(residentDTO.getEmail());
        }
        if (residentDTO.getApartmentNumber() != null) {
            resident.setEmail(residentDTO.getPhone());
        }
        residentRepository.save(resident);
        logger.info("Update profile successfully.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> banResident(String residentId) {
        if (residentId == null) {
            logger.warn("{}" , ServiceMessage.INVALID_ARGUMENT_MESSAGE);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Resident resident = residentRepository.findResidentByIdAndStatus(residentId, Status.ACTIVE);
        if (resident == null) {
            logger.warn("{}", ServiceMessage.ID_NOT_EXIST_MESSAGE);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        resident.setStatus(Status.INACTIVE);
        residentRepository.save(resident);
        logger.info("Ban employee id {} successfully.", residentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
