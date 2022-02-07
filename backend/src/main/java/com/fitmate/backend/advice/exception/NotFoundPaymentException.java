package com.fitmate.backend.advice.exception;
public class NotFoundPaymentException extends RuntimeException{
    public NotFoundPaymentException(){}
    public NotFoundPaymentException(String message){
        super(message);
    }
    public NotFoundPaymentException(String message, Throwable throwable){
        super(message,throwable);
    }
}
