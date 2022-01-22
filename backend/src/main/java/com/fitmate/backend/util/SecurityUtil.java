package com.fitmate.backend.util;

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
            throw new RuntimeException("SecurityContext 에 인증 정보가 없습니다. ");
        }
        return Long.parseLong(authentication.getName());
    }
}
