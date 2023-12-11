package com.es.core.entity.car;

public class carNotFoundException extends RuntimeException {
    public String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public carNotFoundException() {
        this.errorMessage = "car not found!";
    }
}
