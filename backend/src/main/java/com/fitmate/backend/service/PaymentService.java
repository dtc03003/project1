package com.fitmate.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitmate.backend.advice.exception.NotFoundPaymentException;
import com.fitmate.backend.advice.exception.SocialCommunicationException;
import com.fitmate.backend.advice.exception.UpdateStateException;
import com.fitmate.backend.dto.KakaoPayDto;
import com.fitmate.backend.dto.KakaoPayResponseDto;
import com.fitmate.backend.entity.*;
import com.fitmate.backend.repository.PaymentRepository;
import com.fitmate.backend.util.MultiValueMapConverter;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final MemberService memberService;
    private final Environment env;

    public KakaoPayResponseDto payWithKakao(KakaoPayDto kakaoPayDto){
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
    }

    public Payment makePayment(Reservation reservation) {
        return paymentRepository.save(Payment.builder()
                .reservation(reservation)
                .state(State.COMPLETE)
                .build());
    }
    public Payment cancelPayment(Long id){
        Payment payment = paymentRepository.findById(id).orElseThrow(NotFoundPaymentException::new);
        if(payment.getState()==State.CANCEL) throw new UpdateStateException();
        payment.cancel();
        return paymentRepository.save(payment);
    }
    public List<Payment> findAllMyPayments(){
        Member member = memberService.getMyInfo();
        return paymentRepository.findAll()
                .stream()
                .filter(payment -> payment.getReservation().getMember().getId()==member.getId())
                .collect(Collectors.toList());
    }
}
