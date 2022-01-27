package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Qna;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class QnaDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt;

    public static Qna toEntity(QnaDto qnaDto, Member member){
        return Qna.builder()
                .title(qnaDto.getTitle())
                .content(qnaDto.getContent())
                .member(member)
                .build();
    }

    public static QnaDto of(Qna qna){
        return QnaDto.builder()
                .id(qna.getId())
                .title(qna.getTitle())
                .content(qna.getContent())
                .createdAt(qna.getCreatedAt())
                .writer(qna.getMember().getNickname())
                .build();
    }
}
