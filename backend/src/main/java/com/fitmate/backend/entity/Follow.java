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
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long id;
    @ManyToOne
    @JoinColumn(name="follower", nullable = false)
    private Member member;
    @ManyToOne
    @JoinColumn(name = "following", nullable = false)
    private Portfolio stylist;

}
