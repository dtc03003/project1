package com.fitmate.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "chattings")
public class Chatting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "uid")
    private Member member;
    @Column(name = "message")
    private String message;
    @Column(name = "send_at")
    private Timestamp sendAt;
    @Column(name = "is_request")
    private Boolean isRequest;
}
