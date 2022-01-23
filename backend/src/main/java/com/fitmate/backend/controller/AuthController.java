package com.fitmate.backend.controller;

import com.fitmate.backend.dto.LoginDto;
import com.fitmate.backend.dto.TokenDto;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name="AuthController" , description = "로그인 & 회원가입 & 토큰 재발급")
@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<Member> signup(@RequestBody Member member) {
        return ResponseEntity.ok(authService.signup(member));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenDto tokenDto) {
        return ResponseEntity.ok(authService.reissue(tokenDto));
    }
}
