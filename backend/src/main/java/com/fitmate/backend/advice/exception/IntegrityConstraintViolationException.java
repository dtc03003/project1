package com.fitmate.backend.advice.exception;

import com.fitmate.backend.entity.Portfolio;

import java.sql.SQLIntegrityConstraintViolationException;

public class IntegrityConstraintViolationException extends SQLIntegrityConstraintViolationException {
    public IntegrityConstraintViolationException(){}
    public IntegrityConstraintViolationException(String message){
        super(message);
    }
    public IntegrityConstraintViolationException(String message, Throwable throwable){
        super(message,throwable);
    }
}
