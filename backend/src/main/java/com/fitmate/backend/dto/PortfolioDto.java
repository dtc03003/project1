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
    private String about;

    public static Portfolio toEntity(PortfolioDto portfolioDto, Member member){
        return Portfolio.builder()
                .about(portfolioDto.getAbout())
                .member(member)
                .build();
    }
    public static PortfolioDto of(Portfolio portfolio){
        return PortfolioDto.builder()
                .about(portfolio.getAbout())
                .build();
    }
}
