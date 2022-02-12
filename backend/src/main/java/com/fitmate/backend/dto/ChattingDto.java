package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Chatting;
import com.fitmate.backend.entity.Member;
import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChattingDto {
    private String message;
    private Timestamp sendAt;
    private Boolean isRequest;
    public static Chatting toEntity(ChattingDto chattingDto, Member member){
        return Chatting.builder()
                .message(chattingDto.getMessage())
                .sendAt(chattingDto.getSendAt())
                .isRequest(chattingDto.getIsRequest())
                .member(member)
                .build();
    }
}
