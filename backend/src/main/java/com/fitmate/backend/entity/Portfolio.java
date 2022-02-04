package com.fitmate.backend.entity;

import com.fitmate.backend.dto.PortfolioDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "portfolio")
@Getter
@Builder
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long id;
    @Column(length = 1000)
    private String about;
    @OneToOne
    @JoinColumn(name ="stylist" , nullable = false, unique = true)
    private Member member;
    public Portfolio(Portfolio portfolio){
        id = portfolio.getId();
        about = portfolio.getAbout();
        member = portfolio.getMember();
    }
    public Portfolio updatePortfolio(PortfolioDto portfolioDto){
        this.about = portfolioDto.getAbout();
        return this;
    }
}
