package com.fitmate.backend.advice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SocialCommunicationException extends RuntimeException{
    public SocialCommunicationException(String message){
        super(message);
    }
    public SocialCommunicationException(String message, Throwable throwable){
        super(message,throwable);
    }
}
