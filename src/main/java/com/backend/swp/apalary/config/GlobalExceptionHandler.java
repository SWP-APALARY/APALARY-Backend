package com.backend.swp.apalary.config;

import com.backend.swp.apalary.config.exception.IdExistException;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnwantedException(Exception e) {
        logger.error("{}: {}",e.getMessage(),e.getClass());
        return new ResponseEntity<>( "Unknown error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(ServiceException.class)
//    public Response<String> handleCustomException(ServiceException e) {
//        logger.error("{}: {}", e.getCode(), e.getMessage());
//        return new Response<>(e.getCode().value(), e.getMessage());
//    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("{}: {}", HttpStatus.BAD_REQUEST.value(), "Wrong input field type from request");
        return new ResponseEntity<>("Wrong input field type", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<String> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e) {
        logger.error("{}: {}", HttpStatus.BAD_REQUEST.value(), "Incorrect usage of the API");
        return new ResponseEntity<>("Incorrect usage of the API", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error("{}: {}", HttpStatus.BAD_REQUEST.value(), "Missing request parameter from request");
        return new ResponseEntity<>("Missing request parameter", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
        logger.error("{}: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Null Pointer Exception");
        return new ResponseEntity<>("Null Pointer Exception", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        logger.error("{}: {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), "IO Exception");
        return new ResponseEntity<>("IO Exception", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleWrongPassword(BadCredentialsException e) {
        logger.error("{}: {}", HttpStatus.FORBIDDEN.value(), "Wrong username or password!");
        return new ResponseEntity<>("Wrong username or password!", HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDuplicateValue(DataIntegrityViolationException e) {
        logger.info("{}: {}", HttpStatus.BAD_REQUEST.value(), "Duplicate unique value!");
        return new ResponseEntity<>("Duplicate unique value!", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IdExistException.class)
    public ResponseEntity<String> handleIdExist(IdExistException e) {
        logger.info("{}: {}", HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleJwtExpired(ExpiredJwtException e) {
        logger.info("{}: {}", HttpStatus.FORBIDDEN.value(), "Jwt token is expired");
        return new ResponseEntity<>("Jwt token is expired", HttpStatus.FORBIDDEN);
    }
}
