package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundPortfolioException;
import com.fitmate.backend.advice.exception.NotFoundUserInformation;
import com.fitmate.backend.dto.PortfolioDto;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Portfolio;
import com.fitmate.backend.entity.StyleComment;
import com.fitmate.backend.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final MemberService memberService;
    private static final Integer POST_PER_PAGE = 10;

    @Transactional
    public Portfolio makePortfolio(PortfolioDto portfolioDto){
        Member member = memberService.getMyInfo();
        return portfolioRepository.save(PortfolioDto.toEntity(portfolioDto, member));
    }

    public Portfolio getMyPortfolio() {
        Member member = memberService.getMyInfo();
        return portfolioRepository.findPortfolioByMemberId(member.getId())
                .orElseThrow(NotFoundUserInformation::new);
    }

    public Portfolio updateAbout(PortfolioDto portfolioDto) {
        Portfolio portfolio = getMyPortfolio();
        portfolio.updatePortfolio(portfolioDto);
        return portfolioRepository.save(portfolio);
    }

    public Portfolio getPortfolioByNickname(String nickname) {
        return portfolioRepository.findByMember_Nickname(nickname).orElseThrow(NotFoundPortfolioException::new);
    }
    @Transactional
    public String deleteMyPortfolio() {
        Portfolio portfolio = getMyPortfolio();
        String nickname = portfolio.getMember().getNickname();
        portfolioRepository.deleteById(portfolio.getId());
        return nickname;
    }

    public List<Portfolio> findAllPortfoliosOrderByDesc(Integer pageNum) {
        Page<Portfolio> page = portfolioRepository.findAll(
                PageRequest.of(pageNum-1, POST_PER_PAGE, Sort.by(Sort.Direction.DESC, "id")
                ));
        return page.stream()
                .map(Portfolio::new)
                .collect(Collectors.toList());
    }

    public List<Portfolio> findAllPortfolios() {
        return portfolioRepository.findAll();
    }
}
