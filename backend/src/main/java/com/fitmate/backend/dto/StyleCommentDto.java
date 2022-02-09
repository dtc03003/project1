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
    private String comment;
    private LocalDateTime createdAt;

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
                .comment(comment.getComment())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
