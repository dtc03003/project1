package com.fitmate.backend.entity;

import com.fitmate.backend.dto.PortfolioDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private Long price;
    @Column(length = 1000)
    private String bio;
    @OneToOne
    @JoinColumn(name ="stylist" , nullable = false, unique = true)
    private Member member;
    private LocalDateTime latestStyle;
    public Portfolio(Portfolio portfolio){
        id = portfolio.getId();
        about = portfolio.getAbout();
        member = portfolio.getMember();
        price = portfolio.getPrice();
        bio = portfolio.getBio();
    }
    public void updatePrice(Long price){
        this.price = price;
    }
    public void updateBio(String bio){
        this.bio = bio;
    }
    public void updateAbout(String about){
        this.about = about;
    }
    public void updateLatestStyle(LocalDateTime latest){this.latestStyle = latest;}
    public void updatePortfolio(PortfolioDto portfolioDto){
        this.price = portfolioDto.getPrice();
        this.bio = portfolioDto.getBio();
        this.about = portfolioDto.getAbout();
    }
    public String getNickname(){
        return member.getNickname();
    }
}
