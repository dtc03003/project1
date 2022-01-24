package com.fitmate.backend.advice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicatedNicknameException extends RuntimeException {
    public DuplicatedNicknameException(String message){
        super(message);
    }
    public DuplicatedNicknameException(String message, Throwable throwable){
        super(message,throwable);
    }
}
