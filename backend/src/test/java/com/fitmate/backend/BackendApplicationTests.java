package com.fitmate.backend;

import com.fitmate.backend.advice.exception.SocialCommunicationException;
import com.fitmate.backend.dto.SocialMemberDto;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class BackendApplicationTests {
    @Autowired
    private Environment env;
    @Test
    void contextLoads() {
        String kakaoAccessToken = "MRUy6jAzu1iOiQdunRTAD-d7OmS8-eqbHqQDGwopcJ8AAAF-mbIyhg";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.set(env.getProperty("jwt.header"), "Bearer "+kakaoAccessToken);
//        String requestUrl = env.getProperty("social.kakao.url.profile");
//        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(null, headers);
//        try{
//            ResponseEntity<String> response = new RestTemplate().postForEntity(requestUrl,request,String.class);
//            if(response.getStatusCode()== HttpStatus.OK){
//                SocialMemberDto socialMemberDto = new Gson().fromJson(response.getBody(), SocialMemberDto.class);
//                System.out.println(socialMemberDto);
//                return;
//            }
//            System.out.println("header : "+ response.getHeaders());
//        }catch (RuntimeException e){
//            System.out.println(e);
//            throw new SocialCommunicationException();
//        }
//        throw new SocialCommunicationException();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(env.getProperty("jwt.header"), "Bearer "+kakaoAccessToken);
        String requestUrl = env.getProperty("social.kakao.url.profile");
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = new RestTemplate().postForEntity(requestUrl,request,String.class);
        if(response.getStatusCode()!= HttpStatus.OK) {
            throw new SocialCommunicationException();
        }
        SocialMemberDto socialMemberDto = new Gson().fromJson(response.getBody(), SocialMemberDto.class);
        throw new SocialCommunicationException();
    }

}
