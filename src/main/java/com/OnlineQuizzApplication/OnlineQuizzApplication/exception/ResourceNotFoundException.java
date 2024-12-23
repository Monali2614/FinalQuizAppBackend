package com.OnlineQuizzApplication.OnlineQuizzApplication.exception;



public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}