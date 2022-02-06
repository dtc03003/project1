package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundPortfolioException;
import com.fitmate.backend.advice.exception.NotFoundPortfolioReviewException;
import com.fitmate.backend.entity.Grade;
import com.fitmate.backend.entity.Portfolio;
import com.fitmate.backend.entity.Style;
import com.fitmate.backend.repository.FollowRepository;
import com.fitmate.backend.repository.GradeRepository;
import com.fitmate.backend.repository.PortfolioRepository;
import com.fitmate.backend.repository.StyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StylistsService {
    private final GradeRepository gradeRepository;
    private final PortfolioRepository portfolioRepository;
    private final StyleService styleService;

    public List<Portfolio> sortByFollower(){
        List<Grade> grades = gradeRepository.findAll(Sort.by("followCount"));
        List<Portfolio> stylists = new ArrayList<Portfolio>();
        for(int i=0; i<grades.size(); i++){
            stylists.add(grades.get(i).getStylist());
        }
        return stylists;
    }

    public List<Portfolio> sortByGrade(){
        List<Grade> grades = gradeRepository.findAll(Sort.by("grade"));
        List<Portfolio> stylists = new ArrayList<Portfolio>();
        for(int i=0; i<grades.size(); i++){
            stylists.add(grades.get(i).getStylist());
        }
        return stylists;
    }

    public List<Portfolio> sortByLatest(){
        return portfolioRepository.findAll(Sort.by("createdAt").descending());
    }

    public List<Style> latestStylesOfStylist(String stylistNickname){
        Portfolio stylist = portfolioRepository.findByMember_Nickname(stylistNickname).orElseThrow(NotFoundPortfolioReviewException::new);
//        List<Style> styleList = styleRepository.findAllByPortfolioId(stylist.getId(), Sort.by("createdAt").descending());
        List<Style> styleList = styleService.findAllStylesByOrderByIdDesc(1, stylist.getMember().getNickname());
        List<Style> result = new ArrayList<Style>();
        for(int i=0; i<5; i++){
            result.add(styleList.get(i));
        }
        return result;
    }

    public List<Portfolio> searchByStylistNickname(String stylistNickname){
        List<Portfolio> stylists = new ArrayList<Portfolio>();
        stylists.add(portfolioRepository.findByMember_Nickname(stylistNickname).orElseThrow(NotFoundPortfolioException::new));
        List<Portfolio> like = portfolioRepository.findAllByMember_NicknameContaining(stylistNickname).orElseThrow(NotFoundPortfolioException::new);
        for(int i=0; i<like.size(); i++){
            stylists.add(like.get(i));
        }
        return stylists;
    }
}
