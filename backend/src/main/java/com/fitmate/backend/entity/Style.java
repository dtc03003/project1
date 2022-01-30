package com.fitmate.backend.entity;

import com.fitmate.backend.dto.StyleDto;
import lombok.*;

import javax.persistence.*;

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
//    @Column(nullable = false)
    private String thumbnail;
    @ManyToOne
    @JoinColumn(name = "portfolio")
    private Portfolio portfolio;

    public Style updateStyle(StyleDto styleDto){
        content = styleDto.getContent();
        thumbnail = styleDto.getThumbnail();
        return this;
    }
}
