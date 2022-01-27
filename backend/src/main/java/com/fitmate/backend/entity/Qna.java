package com.fitmate.backend.entity;

import com.fitmate.backend.dto.QnaDto;
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
public class Qna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name="writer", nullable = false)
    private Member member;

    @PrePersist
    public void createAt(){ this.createdAt = LocalDateTime.now(); }

    public Qna updateQna(QnaDto qnaDto){
        this.title = qnaDto.getTitle();
        this.content = qnaDto.getContent();
        this.createdAt = LocalDateTime.now();
        return this;
    }
}
