package com.myproject.sample.exception;

public class AccessDeniedException extends Exception {
    public AccessDeniedException(String message){
        super(message);
    }
}
