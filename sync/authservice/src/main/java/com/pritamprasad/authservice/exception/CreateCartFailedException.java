package com.pritamprasad.authservice.exception;

public class CreateCartFailedException extends AuthServiceException{

    public CreateCartFailedException(String exceptionId, String innerExceptionMessage, StackTraceElement[] stackTrace) {
        super(exceptionId, innerExceptionMessage, stackTrace, CreateCartFailedException.class.toString());
    }

    public CreateCartFailedException() {
        super("ex105", null, null, CreateCartFailedException.class.toString());
    }

    public CreateCartFailedException(String msg) {
        super("ex105", msg, null, CreateCartFailedException.class.toString());
    }
}
