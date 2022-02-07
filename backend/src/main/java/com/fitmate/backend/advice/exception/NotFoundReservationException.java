package com.fitmate.backend.advice.exception;
public class NotFoundReservationException extends RuntimeException{
    public NotFoundReservationException(){}
    public NotFoundReservationException(String message){
        super(message);
    }
    public NotFoundReservationException(String message, Throwable throwable){
        super(message,throwable);
    }
}
