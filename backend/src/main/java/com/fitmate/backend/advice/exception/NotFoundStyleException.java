package com.fitmate.backend.advice.exception;

public class NotFoundStyleException extends RuntimeException{
    public NotFoundStyleException(){}
    public NotFoundStyleException(String message){
        super(message);
    }
    public NotFoundStyleException(String message, Throwable throwable){
        super(message,throwable);
    }
}
