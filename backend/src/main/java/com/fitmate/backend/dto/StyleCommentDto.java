package com.fitmate.backend.dto;

import com.fitmate.backend.entity.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StyleCommentDto {
    private Long id;
    private String comment;
    private String writer;
    private LocalDateTime createdAt;
    private Style style;

    public static StyleComment toEntity(StyleCommentDto commentDto, Member member, Style style){
        return StyleComment.builder()
                .comment(commentDto.getComment())
                .member(member)
                .createdAt(commentDto.getCreatedAt())
                .style(style)
                .build();
    }

    public static StyleCommentDto of(StyleComment comment){
        return StyleCommentDto.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .writer(comment.getMember().getNickname())
                .createdAt(comment.getCreatedAt())
                .style(comment.getStyle())
                .build();
    }
}
