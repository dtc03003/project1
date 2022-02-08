package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Portfolio;
import com.fitmate.backend.entity.Reservation;
import com.fitmate.backend.entity.State;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReservationDto {

    private Long cost;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private State state;

    public static Reservation toEntity(ReservationDto reservationDto, Member member, Portfolio portfolio){
        return Reservation.builder()
                .member(member)
                .portfolio(portfolio)
                .cost(reservationDto.cost)
                .state(reservationDto.state)
                .startTime(reservationDto.startTime)
                .endTime(reservationDto.endTime)
                .build();
    }
}
