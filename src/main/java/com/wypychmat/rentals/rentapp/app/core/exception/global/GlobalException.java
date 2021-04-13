package com.wypychmat.rentals.rentapp.app.core.exception.global;

import org.springframework.http.HttpStatus;

public class GlobalException {
    private int errorStatus;
    private String errorDate;
    private String cause;
    private String path;

    public GlobalException(HttpStatus status, String errorDate, String cause, String path) {
        errorStatus = status.value();
        this.errorDate = errorDate;
        this.cause = cause;
        this.path = path;
    }

    public int getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(int errorStatus) {
        this.errorStatus = errorStatus;
    }

    public String getErrorDate() {
        return errorDate;
    }

    public void setErrorDate(String errorDate) {
        this.errorDate = errorDate;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
