package com.fitmate.backend.dto;

import com.fitmate.backend.entity.ChatRoom;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Message;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class MessageDto {
    private String content;
    private Long chatRoomId;
    private Long sender;
    public static Message toEntity(MessageDto messageDto,ChatRoom chatRoom, Member sender){
        return Message.builder()
                .content(messageDto.getContent())
                .chatRoom(chatRoom)
                .sender(sender)
                .build();
    }
}
