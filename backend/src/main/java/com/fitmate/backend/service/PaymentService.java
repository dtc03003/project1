package com.fitmate.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitmate.backend.advice.exception.SocialCommunicationException;
import com.fitmate.backend.dto.KakaoPayDto;
import com.fitmate.backend.dto.KakaoPayResponseDto;
import com.fitmate.backend.util.MultiValueMapConverter;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final Environment env;
    public KakaoPayResponseDto readyKakaopay(KakaoPayDto kakaoPayDto){
        System.out.println("readyKakaopay");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(env.getProperty("jwt.header"),"KakaoAK "+env.getProperty("social.kakao.admin-key"));
        System.out.println("KakaoAK "+env.getProperty("social.kakao.admin-key"));
        String requestUrl = env.getProperty("social.kakao.url.payment.ready");
        MultiValueMap<String,String> params = MultiValueMapConverter.convert(new ObjectMapper(),kakaoPayDto);
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = new RestTemplate().postForEntity(requestUrl, request, String.class);
        if(response.getStatusCode()!= HttpStatus.OK){
            log.error("header : "+ response.getHeaders());
            throw new SocialCommunicationException();
        }
        return new Gson().fromJson(response.getBody(), KakaoPayResponseDto.class);
    }
}
