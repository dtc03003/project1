package com.fitmate.backend.advice.exception;
public class NotFoundPortfolioException extends RuntimeException{
    public NotFoundPortfolioException(){}
    public NotFoundPortfolioException(String message){
        super(message);
    }
    public NotFoundPortfolioException(String message, Throwable throwable){
        super(message,throwable);
    }
}
