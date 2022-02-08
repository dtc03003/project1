package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Carousel;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CarouselDto {
    private String imgSrc;

    public static Carousel toEntity(String imgSrc){
        return Carousel.builder()
                .imgSrc(imgSrc)
                .build();
    }
}
