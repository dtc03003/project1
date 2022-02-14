package com.fitmate.backend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "chat_room_no",nullable = false)
    private ChatRoom chatRoom;
    @ManyToOne
    @JoinColumn(name = "sender_no")
    private Member sender;

}
