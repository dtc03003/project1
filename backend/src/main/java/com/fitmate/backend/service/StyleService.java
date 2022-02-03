package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundStyleException;
import com.fitmate.backend.advice.exception.NotFoundUserInformation;
import com.fitmate.backend.dto.StyleDto;
import com.fitmate.backend.entity.Portfolio;
import com.fitmate.backend.entity.Style;
import com.fitmate.backend.repository.StyleRepository;
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
public class StyleService {
    private final StyleRepository styleRepository;
    private final PortfolioService portfolioService;
    private final MemberService memberService;
    private static final Integer POST_PER_PAGE = 10;

    @Transactional
    public Style postStyle(StyleDto styleDto){
        styleDto.setPortfolio(portfolioService.getMyPortfolio());
        return styleRepository.save(StyleDto.toEntity(styleDto));
    }
    public Style getStyle(Long id){
        return styleRepository.findById(id).orElseThrow(NotFoundUserInformation::new);
    }

    @Transactional
    public List<Style> findAllStylesByOrderByIdDesc(Integer pageNum, String nickname){
        Portfolio portfolio = portfolioService.getPortfolioByNickname(nickname);
        Page<Style> page = styleRepository.findAllByPortfolioId(
                PageRequest.of(pageNum-1, POST_PER_PAGE, Sort.by(Sort.Direction.DESC, "id")
                ),portfolio.getId());
        return page.stream()
                .map(Style::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Style updateStyle(StyleDto styleDto) {
        Style style = styleRepository.findById(styleDto.getId()).orElseThrow(NotFoundStyleException::new);
        style.updateStyle(styleDto);
        return styleRepository.save(style);
    }
}
