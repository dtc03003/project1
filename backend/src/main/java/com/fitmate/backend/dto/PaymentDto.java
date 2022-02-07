package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Payment;
import com.fitmate.backend.entity.Reservation;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PaymentDto {

    private Reservation reservation;
    private Boolean isCanceled;

    public void cancel(){
        this.isCanceled = true;
    }
    public static Payment toEntity(PaymentDto paymentDto, Reservation reservation){
        return Payment.builder()
                .reservation(reservation)
                .isCanceled(paymentDto.getIsCanceled())
                .build();
    }
}
