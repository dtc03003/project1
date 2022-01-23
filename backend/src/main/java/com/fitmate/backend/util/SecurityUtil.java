package com.fitmate.backend.util;

import com.fitmate.backend.advice.exception.NotFoundAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {
    private SecurityUtil() {}
    public static Long getCurrentMemberId(){
        System.out.println("SecurityUtil");
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || authentication.getName() == null){
            throw new NotFoundAuthentication();
        }
        return Long.parseLong(authentication.getName());
    }
}
