package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.DuplicatedEmailException;
import com.fitmate.backend.advice.exception.DuplicatedNicknameException;
import com.fitmate.backend.advice.exception.SocialCommunicationException;
import com.fitmate.backend.dto.*;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.RefreshToken;
import com.fitmate.backend.jwt.TokenProvider;
import com.fitmate.backend.repository.MemberRepository;
import com.fitmate.backend.repository.RefreshTokenRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    private final Environment env;
    @Transactional
    public Member signup(MemberDto memberDto){
        memberDto.setProfile("https://hongjunland.s3.ap-northeast-2.amazonaws.com/default_profile.jpg");
        return memberRepository.save(MemberDto.toEntity(memberDto,passwordEncoder));
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
    public void existByEmail(String email){
        if(memberRepository.existsByEmail(email)) throw new DuplicatedEmailException();
    }
    public void existByNickname(String nickname){
        if(memberRepository.existsByNickname(nickname)) throw new DuplicatedNicknameException();
    }
    @Transactional
    public TokenDto kakaoLogin(KakaoTokenDto kakaoTokenDto){
        SocialMemberDto socialMemberDto = getKakaoProfile(kakaoTokenDto.getAccess_token());
        final String email = socialMemberDto.getKakao_account().getEmail();
        final String password = env.getProperty("social.password");
        System.out.println(email);
        if(!memberRepository.existsByEmail(email)){
            MemberDto memberDto = MemberDto.of(SocialMemberDto.toEntity(socialMemberDto));
            memberDto.setPassword(password);
            memberDto.setSocial("kakao");
            signup(memberDto);
        }
        System.out.println("kakao login!");
        return login(new LoginDto(email,password));
    }
    public SocialMemberDto getKakaoProfile(String kakaoAccessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(env.getProperty("jwt.header"), "Bearer "+kakaoAccessToken);
        String requestUrl = env.getProperty("social.kakao.url.profile");
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = new RestTemplate().postForEntity(requestUrl,request,String.class);
        if(response.getStatusCode()!= HttpStatus.OK){
            log.error("header : "+ response.getHeaders());
            throw new SocialCommunicationException();
        }
        return new Gson().fromJson(response.getBody(), SocialMemberDto.class);
    }
}
