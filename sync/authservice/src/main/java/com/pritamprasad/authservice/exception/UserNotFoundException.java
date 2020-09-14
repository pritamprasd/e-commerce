package com.pritamprasad.authservice.exception;

public class UserNotFoundException extends AuthServiceException{

    public UserNotFoundException(String exceptionId, String innerExceptionMessage, StackTraceElement[] stackTrace) {
        super(exceptionId, innerExceptionMessage, stackTrace, InvalidTokenException.class.toString());
    }

    public UserNotFoundException() {
        super("ex101", null, null, InvalidTokenException.class.toString());
    }
}
