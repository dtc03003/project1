package com.fitmate.backend.entity;

import com.fitmate.backend.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="no")
    private Long id;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String comment;
    @ManyToOne
    @JoinColumn(name = "writer", nullable = false)
    private Member member;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @OneToOne
    @JoinColumn(name = "qna", nullable = false)
    private Qna qna;

    @PrePersist
    public void createdAt(){
        this.createdAt = LocalDateTime.now();
    }

    public Comment updateComment(CommentDto commentDto){
        this.comment = commentDto.getComment();
        this.createdAt = LocalDateTime.now();
        return this;
    }
}
