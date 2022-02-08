package com.fitmate.backend.dto;

import com.fitmate.backend.entity.LikeEntity;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Style;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LikeDto {
    private String nickName;
    private Long styleId;

    public static LikeEntity toEntity(Member member, Style style){
        return LikeEntity.builder()
                .member(member)
                .style(style)
                .build();
    }
}
