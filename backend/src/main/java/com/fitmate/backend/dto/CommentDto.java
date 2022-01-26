package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Qna;
import lombok.*;

import javax.persistence.OneToOne;
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
    private Qna qna;
}
