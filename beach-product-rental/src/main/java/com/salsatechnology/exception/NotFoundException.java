package com.salsatechnology.exception;

public class NotFoundException extends RuntimeException {

    private String message;
    private Object data;

    public NotFoundException(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }
    public Object getData() {
        return data;
    }
}

