package com.fitmate.backend.advice.exception;

public class NotFoundAuthentication extends RuntimeException{
    public NotFoundAuthentication(){}
    public NotFoundAuthentication(String message){
        super(message);
    }
    public NotFoundAuthentication(String message, Throwable throwable){
        super(message,throwable);
    }
}
