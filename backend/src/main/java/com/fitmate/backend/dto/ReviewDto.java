package com.fitmate.backend.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReviewDto {
    private Long id;
    private String content;
    private Integer rating;
}
