package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Portfolio;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PortfolioDto {
    private Long id;
    private String about;
    private Member member;
    public static Portfolio toEntity(PortfolioDto portfolioDto){
        return Portfolio.builder()
                .about(portfolioDto.getAbout())
                .member(portfolioDto.getMember())
                .build();
    }
    public static PortfolioDto of(Portfolio portfolio){
        return PortfolioDto.builder()
                .about(portfolio.getAbout())
                .member(portfolio.getMember())
                .build();
    }
}
