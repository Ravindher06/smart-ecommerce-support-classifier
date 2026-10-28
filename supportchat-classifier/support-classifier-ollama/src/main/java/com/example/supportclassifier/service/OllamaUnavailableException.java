package com.example.supportclassifier.service;

public class OllamaUnavailableException extends RuntimeException {
    public OllamaUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
