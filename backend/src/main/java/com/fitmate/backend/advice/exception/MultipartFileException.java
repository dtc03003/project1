package com.fitmate.backend.advice.exception;

public class MultipartFileException extends IllegalStateException{
    public MultipartFileException(){}
    public MultipartFileException(String message){
        super(message);
    }
    public MultipartFileException(String message, Throwable throwable){
        super(message,throwable);
    }
}
