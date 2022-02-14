package com.fitmate.backend.advice.exception;
public class NotFoundChatRoom extends RuntimeException{
    public NotFoundChatRoom(){}
    public NotFoundChatRoom(String message){
        super(message);
    }
    public NotFoundChatRoom(String message, Throwable throwable){
        super(message,throwable);
    }
}
