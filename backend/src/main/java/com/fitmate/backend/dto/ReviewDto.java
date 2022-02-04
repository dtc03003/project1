package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Portfolio;
import com.fitmate.backend.entity.Review;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReviewDto {
    private String content;
    private Integer rating;
    private String thumbnail;

    public static Review toEntity(ReviewDto reviewDto, Member member, Portfolio portfolio) {
        return Review.builder()
                .member(member)
                .portfolio(portfolio)
                .content(reviewDto.getContent())
                .rating(reviewDto.getRating())
                .thumbnail(reviewDto.getThumbnail())
                .build();
    }
}
