package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Comment;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Qna;
import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentDto {
    private Long id;
    private String comment;
    private String writer;
    private LocalDateTime createdAt;
    private Long qnaId;

    public static Comment toEntity(CommentDto commentDto, Member member, Qna qna){
        return Comment.builder()
                .comment(commentDto.getComment())
                .member(member)
                .createdAt(commentDto.getCreatedAt())
                .qna(qna)
                .build();
    }

    public static CommentDto of(Comment comment){
        return CommentDto.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .writer(comment.getMember().getNickname())
                .createdAt(comment.getCreatedAt())
                .qnaId(comment.getQna().getId())
                .build();
    }
}
