package com.fitmate.backend.entity;

import com.fitmate.backend.dto.ReviewDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long id;
    @Column(length = 1000)
    private String content;
    private Integer rating;
    private String thumbnail;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "portfolio")
    private Portfolio portfolio;
    @ManyToOne
    @JoinColumn(name = "writer")
    private Member member;

    @PrePersist
    public void createdAt(){
        this.createdAt = LocalDateTime.now();
    }
    public Review(Review review){
        id = review.getId();
        content = review.getContent();
        rating = review.getRating();
        thumbnail = review.getThumbnail();
        createdAt = review.getCreatedAt();
        portfolio = review.getPortfolio();
        member = review.getMember();
    }
    public Review updateReview(ReviewDto reviewDto){
        content = reviewDto.getContent();
        rating = reviewDto.getRating();
        thumbnail = reviewDto.getThumbnail();
        return this;
    }
    public String getWriterNickName(){
        return member.getNickname();
    }
}
