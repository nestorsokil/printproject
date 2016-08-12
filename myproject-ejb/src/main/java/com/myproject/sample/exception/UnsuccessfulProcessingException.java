package com.myproject.sample.exception;

public class UnsuccessfulProcessingException extends Exception {
    public UnsuccessfulProcessingException(Throwable t){
        super(t);
    }
}
