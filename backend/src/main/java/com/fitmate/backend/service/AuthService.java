package com.fitmate.backend.service;

import com.fitmate.backend.dto.LoginDto;
import com.fitmate.backend.dto.MemberDto;
import com.fitmate.backend.dto.TokenDto;
import com.fitmate.backend.entity.Authority;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.RefreshToken;
import com.fitmate.backend.jwt.TokenProvider;
import com.fitmate.backend.repository.MemberRepository;
import com.fitmate.backend.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Member signup(Member member){
        validateDuplicateMember(member);
        member.encodingPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member);
    }
    @Transactional
    public TokenDto login(LoginDto loginDto){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);
        return tokenDto;
    }
    @Transactional
    public TokenDto reissue(TokenDto tokenDto){
        if (!tokenProvider.validateToken(tokenDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto newTokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(newTokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return newTokenDto;
    }

    private void validateDuplicateMember(Member member){
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m -> {throw new IllegalStateException("The email already exists by email");});

        memberRepository.findByNickname(member.getNickname())
                .ifPresent(m -> {throw new IllegalStateException("The email already exists by nickname");});
    }
}
