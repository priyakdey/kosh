package com.priyakdey.backend.exception;

import java.io.Serial;

/**
 * @author Priyak Dey
 */
public class AuthException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 828031872122277740L;

    public AuthException() {
        super();
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

    protected AuthException(String message, Throwable cause,
                            boolean enableSuppression,
                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
