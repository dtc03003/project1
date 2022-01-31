package com.fitmate.backend.controller;

import com.fitmate.backend.dto.*;
import com.fitmate.backend.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="AuthController" , description = "로그인 & 회원가입 & 토큰 재발급")
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;
    @Value("${url.base}")
    private String baseUrl;
    @Value("${social.kakao.client-id}")
    private String kakaoClientId;
    @Value("${social.kakao.redirect}")
    private String kakaoRedirectUri;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(authService.signup(memberDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenDto tokenDto) {
        return ResponseEntity.ok(authService.reissue(tokenDto));
    }
    @GetMapping("/signup/email/{email}")
    public void confirmDuplicateEmail(@PathVariable String email ){
        authService.existByEmail(email);
    }

    @GetMapping("/signup/nickname/{nickname}")
    public void confirmDuplicateNickName(@PathVariable String nickname ){
        authService.existByNickname(nickname);
    }

    @PostMapping("/login/kakao")
    public ResponseEntity<TokenDto> kakaoLogin(@RequestBody KakaoTokenDto kakaoTokenDto){
        System.out.println(kakaoTokenDto.getAccess_token());
        System.out.println("controller");
        return ResponseEntity.ok(authService.kakaoLogin(kakaoTokenDto));
    }
}
