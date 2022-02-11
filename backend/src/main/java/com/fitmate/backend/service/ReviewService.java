package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundPortfolioReviewException;
import com.fitmate.backend.dto.ReviewDto;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Portfolio;
import com.fitmate.backend.entity.Review;
import com.fitmate.backend.repository.ReviewRepository;
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
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberService memberService;
    private final PortfolioService portfolioService;
    private static final Integer POST_PER_PAGE = 5;
    @Transactional
    public Review writeReview(String nickname, ReviewDto reviewDto) {
        Member member = memberService.getMyInfo();
        Portfolio portfolio = portfolioService.getPortfolioByNickname(nickname);
        Review review = ReviewDto.toEntity(reviewDto,member,portfolio);
        return reviewRepository.save(review);
    }
    public Review findById(Long id) {
        return reviewRepository.findById(id).orElseThrow(NotFoundPortfolioReviewException::new);
    }

    public List<Review> findAllReviewsByPortfolioNickname(Integer pageNum, String nickname) {
        Portfolio portfolio = portfolioService.getPortfolioByNickname(nickname);
        Page<Review> page = reviewRepository.findAllByPortfolioId(
                PageRequest.of(pageNum-1, POST_PER_PAGE, Sort.by(Sort.Direction.DESC, "id")
                ),portfolio.getId());
        return page.stream()
                .map(Review::new)
                .collect(Collectors.toList());
    }

    public List<Review> findAll(String nickname) {
        Portfolio portfolio = portfolioService.getPortfolioByNickname(nickname);
        return reviewRepository.findAllByPortfolioId(portfolio.getId());
    }

    public List<Review> findAllByMemberId(Long id) {
        return reviewRepository.findAllByMemberId(id);
    }
}
