package com.pritamprasad.authservice.exception;

public class InvalidTokenException extends AuthServiceException{
    public InvalidTokenException(String exceptionId, String innerExceptionMessage, StackTraceElement[] stackTrace) {
        super(exceptionId, innerExceptionMessage, stackTrace, InvalidTokenException.class.toString());
    }

    public InvalidTokenException() {
        super("ex103", null, null, InvalidTokenException.class.toString());
    }
}
