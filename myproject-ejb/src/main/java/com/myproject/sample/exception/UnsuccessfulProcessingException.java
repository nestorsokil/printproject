package com.myproject.sample.exception;

public class UnsuccessfulProcessingException extends Exception {
    public UnsuccessfulProcessingException(Throwable t){
        super(t);
    }

    public UnsuccessfulProcessingException(String message){
        super(message);
    }
}
