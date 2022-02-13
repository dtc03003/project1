package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundPortfolioException;
import com.fitmate.backend.advice.exception.NotFoundPortfolioReviewException;
import com.fitmate.backend.entity.Grade;
import com.fitmate.backend.entity.Portfolio;
import com.fitmate.backend.entity.Style;
import com.fitmate.backend.repository.GradeRepository;
import com.fitmate.backend.repository.PortfolioRepository;
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
        List<Grade> grades = gradeRepository.findAll(Sort.by("countOfFollower").descending());
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
        return portfolioRepository.findAll(Sort.by("latestStyle").descending());
    }

    public List<Style> latestStylesOfStylist(String stylistNickname){
        Portfolio stylist = portfolioRepository.findByMember_Nickname(stylistNickname).orElseThrow(NotFoundPortfolioReviewException::new);
        List<Style> styleList = styleService.findAllStylesByOrderByIdDesc(1, stylist.getMember().getNickname());
        List<Style> result = new ArrayList<Style>();
        for(int i=0; i<styleList.size()&&i<5; i++){
            result.add(styleList.get(i));
        }
        return result;
    }

    public List<Portfolio> searchByStylistNickname(String stylistNickname){
        List<Portfolio> stylists = new ArrayList<Portfolio>();
        List<Portfolio> like = portfolioRepository.findByMember_NicknameContaining(stylistNickname);
        for(int i=0; i<like.size(); i++){
            stylists.add(like.get(i));
        }
        return stylists;
    }
}
