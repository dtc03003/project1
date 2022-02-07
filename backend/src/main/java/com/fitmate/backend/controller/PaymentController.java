package com.fitmate.backend.controller;

import com.fitmate.backend.dto.KakaoPayDto;
import com.fitmate.backend.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="PaymentController" , description = "결제 관리")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/payment")
    public ResponseEntity<?> readyKakaopay(@RequestBody KakaoPayDto kakaoPayDto){
        return ResponseEntity.ok(paymentService.readyKakaopay(kakaoPayDto));
    }
}
