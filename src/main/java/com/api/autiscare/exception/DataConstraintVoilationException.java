package com.api.autiscare.exception;

/**
 * @author divyesh.dwivedi
 */

public class DataConstraintVoilationException extends Exception{
    public DataConstraintVoilationException() {
        super();
    }

    public DataConstraintVoilationException(String message) {
        super(message);
    }

    public DataConstraintVoilationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataConstraintVoilationException(Throwable cause) {
        super(cause);
    }

    protected DataConstraintVoilationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
