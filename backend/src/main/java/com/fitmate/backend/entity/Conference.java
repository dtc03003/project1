package com.fitmate.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "owner")
    private Portfolio portfolio;
    private LocalDateTime callStartTime;
    private String title;
    private String description;
    private Boolean isActivate;
    @ManyToOne
    @JoinColumn(name = "customer")
    private Member member;
}
