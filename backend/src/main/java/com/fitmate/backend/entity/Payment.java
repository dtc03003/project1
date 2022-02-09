package com.fitmate.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long id;

    @OneToOne
    @JoinColumn(name = "reservation_no")
    private Reservation reservation;
    private State state;

    public void cancel(){state = State.CANCEL;}
    public void complete(){state = State.COMPLETE;}
}
