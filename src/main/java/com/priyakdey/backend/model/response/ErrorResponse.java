package com.priyakdey.backend.model.response;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Priyak Dey
 */
public class ErrorResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -5594746651142688977L;

    private String errorCode;
    private String errorMessage;

    public ErrorResponse() {
    }

    public ErrorResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
