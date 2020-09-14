package com.pritamprasad.authservice.advice;

import com.pritamprasad.authservice.exception.AuthServiceException;
import com.pritamprasad.authservice.exception.InvalidTokenException;
import com.pritamprasad.authservice.exception.UserNotFoundException;
import com.pritamprasad.authservice.models.AuthServiceExceptionResponse;
import com.pritamprasad.authservice.models.ExceptionConfig;
import com.pritamprasad.authservice.util.ConfigModelReader;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Optional;

import static com.pritamprasad.authservice.util.ConfigModelReader.getExceptionForId;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class AuthControllerAdvice {

    private Logger LOG = LoggerFactory.getLogger("UnhandledInternalException");

    private static final String SEPARATOR =  " | ";

    /**
     * TODO: Log the requestbody for which any exceptions ocuurred.
     */
    @ExceptionHandler(AuthServiceException.class)
    public ResponseEntity<AuthServiceExceptionResponse> notFoundException(final AuthServiceException e) {
        LOG.error("AuthServiceException occurred...");
        LOG.error(e.toString());
        if(e instanceof  UserNotFoundException){
            return createUserNotFoundResponse(e);
        }
        if(e instanceof  InvalidTokenException){
            return createInvalidTokenExceptionResponse(e);
        }
        return null;
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<AuthServiceExceptionResponse> unExpectedException(final Throwable e) {
        LOG.error(e.getMessage());
        LOG.error(Arrays.toString(e.getStackTrace()));
        AuthServiceExceptionResponse.builder()
                .exceptionId("ex107")
                .timestamp(new Timestamp(System.currentTimeMillis()).toString())
                .message(Optional.ofNullable(getExceptionForId("ex107")).get().orElse(new ExceptionConfig()).getUser_message())
                .build();
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<AuthServiceExceptionResponse> createInvalidTokenExceptionResponse(AuthServiceException e) {
        return new ResponseEntity<>(createResponse(e),HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<AuthServiceExceptionResponse> createUserNotFoundResponse(AuthServiceException e) {
        return new ResponseEntity<>(createResponse(e),HttpStatus.NOT_FOUND);
    }

    private AuthServiceExceptionResponse createResponse(AuthServiceException e) {
        return AuthServiceExceptionResponse.builder()
                .exceptionId(e.getExceptionId())
                .timestamp(e.getTimestamp().toString())
                .message(createMessage(e))
                .build();
    }

    private String createMessage(AuthServiceException e) {
        return "AuthServiceException Occurred! " +
                SEPARATOR +
                Optional.ofNullable(getExceptionForId(e.getExceptionId())).get().orElse(new ExceptionConfig()).getUser_message();
    }
}
