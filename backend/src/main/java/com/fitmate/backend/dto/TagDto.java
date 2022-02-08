package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Style;
import com.fitmate.backend.entity.Tag;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TagDto {
    private Long styleId;
    private List<String> tag;

    public static Tag toEntity(Style style, String tag){
        return Tag.builder()
                .style(style)
                .tag(tag)
                .build();
    }
}
