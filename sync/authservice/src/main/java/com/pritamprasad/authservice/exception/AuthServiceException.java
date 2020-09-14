package com.pritamprasad.authservice.exception;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Exception only handles exception/error related data.
 * Error ResponseCode needs to be handled at  ControllerAdvice level
 * TODO:
 * 1. Exception vs Error vs RunTimeException
 * 2.
 *
 * @since : 0.0.1
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class AuthServiceException extends Exception{
    /**
     * Defined in resources/application.yaml
     */
    private String exceptionId;

    private String innerExceptionMessage;

    private StackTraceElement[] stackTrace;

    @Setter
    private String exceptionClass;

    private Timestamp timestamp;

    public AuthServiceException(String exceptionId, String innerExceptionMessage, StackTraceElement[] stackTrace, String exceptionClass) {
        this.exceptionId = exceptionId;
        this.innerExceptionMessage = innerExceptionMessage;
        this.stackTrace = stackTrace;
        this.exceptionClass = exceptionClass;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }
}
