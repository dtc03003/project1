package com.fitmate.backend.advice.exception;
public class NotFoundPortfolioReviewException extends RuntimeException{
    public NotFoundPortfolioReviewException(){}
    public NotFoundPortfolioReviewException(String message){
        super(message);
    }
    public NotFoundPortfolioReviewException(String message, Throwable throwable){
        super(message,throwable);
    }
}
