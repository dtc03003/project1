package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Portfolio;
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

    private Long id;
    private Long cost;
    private Portfolio portfolio;
    private Member member;
    private LocalDateTime createdAt;
    private Date startTime;
    private Date endTime;

    @PrePersist
    public void createdAt(){
        this.createdAt = LocalDateTime.now();
    }

    public ReservationDto(ReservationDto reservation){
        id = reservation.getId();
        cost = reservation.getCost();
        portfolio = reservation.getPortfolio();
        member = reservation.getMember();
        createdAt = reservation.getCreatedAt();
        startTime = reservation.getStartTime();
        endTime = reservation.getEndTime();
    }

}
