package com.fitmate.backend.entity;

import com.fitmate.backend.dto.NoticeDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "writer", nullable = false)
    private Member member;

    @PrePersist
    public void createdAt(){
        this.createdAt = LocalDateTime.now();
    }

    public Notice updateNotice(NoticeDto noticeDto){
        this.title = noticeDto.getTitle();
        this.content = noticeDto.getContent();
        this.createdAt = LocalDateTime.now();
        return this;
    }
}
