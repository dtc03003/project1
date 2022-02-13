package com.fitmate.backend.service;

import com.fitmate.backend.dto.ChattingDto;
import com.fitmate.backend.entity.Chatting;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.repository.ChattingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChattingService {
    private final MemberService memberService;
    private final ChattingRepository chattingRepository;

    public Chatting chattingHandler(ChattingDto chattingDto){
        System.out.println(chattingDto.getMessage());
        Member member = memberService.getMyInfo();
        return chattingRepository.save(ChattingDto.toEntity(chattingDto,member));
    }
}
/*
    HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.set(env.getProperty("jwt.header"),"KakaoAK "+env.getProperty("social.kakao.admin-key"));
                String requestUrl = env.getProperty("social.kakao.url.payment.ready");
                MultiValueMap<String,String> params = MultiValueMapConverter.convert(new ObjectMapper(),kakaoPayDto);
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = new RestTemplate().postForEntity(requestUrl, request, String.class);
        if(response.getStatusCode()!= HttpStatus.OK){
        log.error("header : "+ response.getHeaders());
        throw new SocialCommunicationException();
        }
        return new Gson().fromJson(response.getBody(), KakaoPayResponseDto.class);

*/