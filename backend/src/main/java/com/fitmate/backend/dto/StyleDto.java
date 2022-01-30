package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Portfolio;
import com.fitmate.backend.entity.Style;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StyleDto {
    private Long id;
    private String content;
    private String thumbnail;
    private Portfolio portfolio;
    public static Style toEntity(StyleDto styleDto){
        return Style.builder()
                .content(styleDto.getContent())
                .thumbnail(styleDto.getThumbnail())
                .portfolio(styleDto.getPortfolio())
                .build();
    }
}
