package com.example.cinema.exception;

public class RequestException {
    private String error;

    public RequestException(String error) {
        this.error = error;
    }
    public String getError() {
        return error;
    }
}
