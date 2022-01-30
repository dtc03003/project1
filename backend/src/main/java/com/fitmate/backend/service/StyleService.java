package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundUserInformation;
import com.fitmate.backend.dto.StyleDto;
import com.fitmate.backend.entity.Portfolio;
import com.fitmate.backend.entity.Style;
import com.fitmate.backend.repository.StyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class StyleService {
    private final StyleRepository styleRepository;
    private final PortfolioService portfolioService;
    private final MemberService memberService;
    @Transactional
    public Style postStyle(StyleDto styleDto){
        styleDto.setPortfolio(portfolioService.getMyPortfolio());
        return styleRepository.save(StyleDto.toEntity(styleDto));
    }
    public Style getStyle(Long id){
        return styleRepository.findById(id).orElseThrow(NotFoundUserInformation::new);
    }

}
