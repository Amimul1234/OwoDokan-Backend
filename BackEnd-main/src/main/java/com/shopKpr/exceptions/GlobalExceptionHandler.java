package com.shopKpr.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExists.class)
    public ResponseEntity<ExceptionResponse> resourceAlreadyExists( AlreadyExists ex ) {
        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("CONFLICT");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> disabledException( DisabledException ex ) {
        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("FORBIDDEN");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> badCredentialException( BadCredentialsException ex ) {
        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("UNAUTHORIZED");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ImageCreationException.class)
    public ResponseEntity<ExceptionResponse> imageCreationFailed( ImageCreationException ex ) {
        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("FAILED");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler(MobileNumberMisMatch.class)
    public ResponseEntity<ExceptionResponse> mobileMismatch( MobileNumberMisMatch ex ) {
        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("Wrong Number");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(EmailAddressMismatch.class)
    public ResponseEntity<ExceptionResponse> mobileMismatch( EmailAddressMismatch ex ) {
        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("Wrong Email Address");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(CategoryCreationException.class)
    public ResponseEntity<ExceptionResponse> categoryCreationException( CategoryCreationException categoryCreationException ) {
        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("Can not create category");
        response.setErrorMessage(categoryCreationException.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionResponse> categoryNotFoundException( CategoryNotFoundException categoryNotFoundException ) {
        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("Category not found");
        response.setErrorMessage(categoryNotFoundException.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ExceptionResponse> unauthorizedException( UnAuthorizedException ex ) {

        ExceptionResponse response = new ExceptionResponse();

        response.setErrorCode("UNAUTHORIZED");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
