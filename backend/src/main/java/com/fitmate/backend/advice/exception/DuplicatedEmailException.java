package com.fitmate.backend.advice.exception;

import com.fitmate.backend.entity.Member;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicatedEmailException extends RuntimeException {
    public DuplicatedEmailException(String message){
        super(message);
    }
    public DuplicatedEmailException(String message, Throwable throwable){
        super(message,throwable);
    }
}
