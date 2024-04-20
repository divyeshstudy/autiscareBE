package com.api.autiscare.exception;

/**
 * @author divyesh.dwivedi
 */

public class NullValueFound extends Exception {
    public NullValueFound() {
        super();
    }

    public NullValueFound(String message) {
        super(message);
    }

    public NullValueFound(String message, Throwable cause) {
        super(message, cause);
    }

    public NullValueFound(Throwable cause) {
        super(cause);
    }

    protected NullValueFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
