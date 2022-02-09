package com.fitmate.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "reservation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long id;

    @Column(nullable = false)
    private Long cost;

    @ManyToOne
    @JoinColumn(name = "portfolio")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "customer")
    private Member member;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private LocalDateTime endTime;

    private State state;

    @PrePersist
    public void createdAt(){
        this.createdAt = LocalDateTime.now();
    }

    public Reservation(Reservation reservation){
        id = reservation.getId();
        cost = reservation.getCost();
        portfolio = reservation.getPortfolio();
        member = reservation.getMember();
        createdAt = reservation.getCreatedAt();
        startTime = reservation.getStartTime();
        endTime = reservation.getEndTime();
        state = reservation.getState();
    }
    public void cancel(){state = State.CANCEL;}
    public void complete(){state = State.COMPLETE;}
    public Long getBetween(){
        return ChronoUnit.HOURS.between(startTime,endTime);
    }
    public Integer getHour(){
        return startTime.getHour();
    }
}
