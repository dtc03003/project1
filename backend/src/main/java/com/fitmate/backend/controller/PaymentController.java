package com.fitmate.backend.controller;

import com.fitmate.backend.dto.KakaoPayDto;
import com.fitmate.backend.dto.PaymentDto;
import com.fitmate.backend.entity.Reservation;
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

    @PostMapping("/payment/ready")
    public ResponseEntity<?> payWithKakao(@RequestBody KakaoPayDto kakaoPayDto){
        return ResponseEntity.ok(paymentService.payWithKakao(kakaoPayDto));
    }
    @PostMapping("/payment")
    public ResponseEntity<?> makePayment(@RequestBody Reservation reservation){
        return ResponseEntity.ok(paymentService.makePayment(reservation));
    }
    @PutMapping("/payment/{id}")
    public ResponseEntity<?> cancelPayment(@PathVariable Long id){
        return ResponseEntity.ok(paymentService.cancelPayment(id));
    }
    @GetMapping("/payments/me")
    public ResponseEntity<?> findAllMyPayments(){
        return ResponseEntity.ok(paymentService.findAllMyPayments());
    }
}
