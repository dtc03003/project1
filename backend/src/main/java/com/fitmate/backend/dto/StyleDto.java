package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Member;
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
    private String content;
    private String thumbnail;
    public static Style toEntity(StyleDto styleDto, Portfolio portfolio){
        return Style.builder()
                .content(styleDto.getContent())
                .thumbnail(styleDto.getThumbnail())
                .portfolio(portfolio)
                .build();
    }
}
