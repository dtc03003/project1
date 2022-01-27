package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Notice;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NoticeDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt;

    public static Notice toEntity(NoticeDto noticeDto, Member member){
        return Notice.builder()
                .title(noticeDto.title)
                .content(noticeDto.content)
                .member(member)
                .build();
    }

    public static NoticeDto of(Notice notice){
        return NoticeDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .writer(notice.getMember().getNickname())
                .build();
    }
}
