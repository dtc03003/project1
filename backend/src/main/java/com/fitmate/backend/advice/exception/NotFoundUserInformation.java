package com.fitmate.backend.advice.exception;

import java.util.NoSuchElementException;

public class NotFoundUserInformation  extends RuntimeException {
    public NotFoundUserInformation(){}
    public NotFoundUserInformation(String message){
        super(message);
    }
    public NotFoundUserInformation(String message, Throwable throwable){
        super(message,throwable);
    }
}
