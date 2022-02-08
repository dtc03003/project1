package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Payment;
import com.fitmate.backend.entity.Reservation;
import com.fitmate.backend.entity.State;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PaymentDto {

    private Reservation reservation;
    private State state;

    public static Payment toEntity(PaymentDto paymentDto, Reservation reservation){
        return Payment.builder()
                .reservation(reservation)
                .state(paymentDto.getState())
                .build();
    }
}
