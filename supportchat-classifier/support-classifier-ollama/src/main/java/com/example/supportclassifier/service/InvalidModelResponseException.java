package com.example.supportclassifier.service;

public class InvalidModelResponseException extends RuntimeException {
    public InvalidModelResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
