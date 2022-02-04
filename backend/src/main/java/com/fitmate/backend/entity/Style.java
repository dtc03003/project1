package com.fitmate.backend.entity;

import com.fitmate.backend.dto.StyleDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "style")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Style{
    @Id
    @Column(name="no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @Column(nullable = false)
    private String thumbnail;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "portfolio")
    private Portfolio portfolio;

    @PrePersist
    public void createdAt(){
        this.createdAt = LocalDateTime.now();
    }

    public Style(Style style) {
        id = style.getId();
        content = style.getContent();
        thumbnail = style.getThumbnail();
        portfolio = style.getPortfolio();
        createdAt = style.getCreatedAt();
    }

    public Style updateStyle(StyleDto styleDto){
        content = styleDto.getContent();
        thumbnail = styleDto.getThumbnail();
        return this;
    }
}
