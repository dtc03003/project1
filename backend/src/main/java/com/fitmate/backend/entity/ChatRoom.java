package com.fitmate.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String title;
    @OneToOne
    @JoinColumn(name = "host_no")
    private Member host;
    private String accessCode;
    @PrePersist
    public void accessCode(){
        this.accessCode = host.getNickname()+UUID.randomUUID();
    }
    public void updateAccessCode(){
        this.accessCode = host.getNickname()+UUID.randomUUID();
    }
}