package com.fitmate.backend.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiError{
    private HttpStatus status;
    private String code;
    private String message;
    @Builder
    public ApiError(HttpStatus status, String code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
