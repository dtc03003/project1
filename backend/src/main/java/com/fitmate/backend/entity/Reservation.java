package com.fitmate.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

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
    @JoinColumn(name = "portfolio_no")
    @Column(nullable = false)
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "customer_no")
    @Column(nullable = false)
    private Member member;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Date startTime;
    @Column(nullable = false)
    private Date endTime;

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
    }

}
