package com.api.autiscare.exception;

/**
 * @author divyesh.dwivedi
 */

public class DuplicateRecordsException extends Exception{
    public DuplicateRecordsException() {
        super();
    }

    public DuplicateRecordsException(String message) {
        super(message);
    }

    public DuplicateRecordsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateRecordsException(Throwable cause) {
        super(cause);
    }

    protected DuplicateRecordsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
