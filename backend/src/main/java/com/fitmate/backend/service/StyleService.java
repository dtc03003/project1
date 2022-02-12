package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundStyleException;
import com.fitmate.backend.advice.exception.NotFoundUserInformation;
import com.fitmate.backend.dto.StyleDto;
import com.fitmate.backend.entity.Portfolio;
import com.fitmate.backend.entity.Style;
import com.fitmate.backend.entity.StyleComment;
import com.fitmate.backend.entity.Tag;
import com.fitmate.backend.repository.*;
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
    private final PortfolioRepository portfolioRepository;
    private final StyleCommentRepository styleCommentRepository;
    private final LikeRepository likeRepository;
    private final TagRepository tagRepository;
    private static final Integer POST_PER_PAGE = 10;

    @Transactional
    public Style postStyle(StyleDto styleDto){
        Portfolio portfolio = portfolioService.getMyPortfolio();
        Style style = styleRepository.save(StyleDto.toEntity(styleDto,portfolio));
        Style resultStyle = styleRepository.findById(style.getId()).orElseThrow(NotFoundStyleException::new);
        portfolio.updateLatestStyle(resultStyle.getCreatedAt());
        portfolioRepository.save(portfolio);
        return style;
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
    public String deleteStyle(Long id){
        Style style = styleRepository.findById(id).orElseThrow(NotFoundStyleException::new);
        tagRepository.deleteAllByStyle(style);
        likeRepository.deleteAllByStyle(style);
        styleCommentRepository.deleteAllByStyle(style);
        styleRepository.delete(style);
        return "delete Success";
    }

    @Transactional
    public Style updateStyle(StyleDto styleDto, Long id) {
        Style style = styleRepository.findById(id).orElseThrow(NotFoundStyleException::new);
        style.updateStyle(styleDto);
        return styleRepository.save(style);
    }
    public Style findById(Long id){
        return styleRepository.findById(id).orElseThrow(NotFoundStyleException::new);
    }

    public List<Style> findAllStylesByNickname(String nickname) {
        Portfolio portfolio = portfolioService.getPortfolioByNickname(nickname);
        return styleRepository.findAllByPortfolioIdOrderByIdDesc(portfolio.getId());
    }
}
