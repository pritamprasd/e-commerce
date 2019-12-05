package com.pritamprasad.itemservice.advice;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class ItemControllerAdvice {
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<VndErrors> notFoundException(final EmptyResultDataAccessException e) {
        return error(e, HttpStatus.NOT_FOUND, String.valueOf(System.currentTimeMillis()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<VndErrors> invalidTokenException(final EmptyResultDataAccessException e) {
        return error(e, HttpStatus.BAD_REQUEST, String.valueOf(System.currentTimeMillis()));
    }

    private ResponseEntity < VndErrors > error(final Exception exception, final HttpStatus httpStatus, final String logRef) {
        final String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        return new ResponseEntity < > (new VndErrors(logRef, message), httpStatus);
    }
}
