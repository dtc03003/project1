package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.IntegrityConstraintViolationException;
import com.fitmate.backend.advice.exception.NotFoundUserInformation;
import com.fitmate.backend.dto.PortfolioDto;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Portfolio;
import com.fitmate.backend.entity.Style;
import com.fitmate.backend.repository.PortfolioRepository;
import com.fitmate.backend.repository.StyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final MemberService memberService;

    @Transactional
    public Portfolio makePortfolio(PortfolioDto portfolioDto){
        portfolioDto.setMember(memberService.getMyInfo());
        return portfolioRepository.save(PortfolioDto.toEntity(portfolioDto));
    }

    public Portfolio getMyPortfolio() {
        Member member = memberService.getMyInfo();
        return portfolioRepository.findPortfolioByMemberId(member.getId())
                .orElseThrow(NotFoundUserInformation::new);
    }

    public Portfolio updateAbout(PortfolioDto portfolioDto) {
        return portfolioRepository.save(getMyPortfolio().updatePortfolio(portfolioDto));
    }
}
