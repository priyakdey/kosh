package com.priyakdey.backend.exception;

import java.io.Serial;

/**
 * @author Priyak Dey
 */
public class ProfileNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1621590830118405293L;

    public ProfileNotFoundException() {
        super();
    }

    public ProfileNotFoundException(String message) {
        super(message);
    }

    public ProfileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProfileNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ProfileNotFoundException(String message, Throwable cause,
                                       boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
