package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundPortfolioException;
import com.fitmate.backend.advice.exception.NotFoundUserInformation;
import com.fitmate.backend.dto.GradeDto;
import com.fitmate.backend.entity.Grade;
import com.fitmate.backend.entity.Portfolio;
import com.fitmate.backend.repository.GradeRepository;
import com.fitmate.backend.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;
    private final PortfolioRepository portfolioRepository;

    @Transactional
    public GradeDto calculateGrade(GradeDto gradeDto){
        Portfolio stylist = portfolioRepository.findByMember_Nickname(gradeDto.getStylistNickName()).orElseThrow(NotFoundPortfolioException::new);
        Grade grade = gradeRepository.findByStylist(stylist).orElseThrow(NotFoundUserInformation::new);
        grade.updateGrade(gradeDto.getGrade());
        return GradeDto.of(grade);
    }

    public float findGradeByStylist(String stylistNickname){
        Portfolio stylist = portfolioRepository.findByMember_Nickname(stylistNickname).orElseThrow(NotFoundPortfolioException::new);
        Grade grade = gradeRepository.findByStylist(stylist).orElseThrow(NotFoundPortfolioException::new);
        return grade.getGrade();
    }
}
