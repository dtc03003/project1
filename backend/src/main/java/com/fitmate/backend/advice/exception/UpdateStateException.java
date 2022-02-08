package com.fitmate.backend.advice.exception;

public class UpdateStateException extends RuntimeException{
    public UpdateStateException(){}
    public UpdateStateException(String message){
        super(message);
    }
    public UpdateStateException(String message, Throwable throwable){
        super(message,throwable);
    }
}
