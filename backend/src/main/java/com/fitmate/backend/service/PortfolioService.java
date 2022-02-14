package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundPortfolioException;
import com.fitmate.backend.advice.exception.NotFoundUserInformation;
import com.fitmate.backend.dto.GradeDto;
import com.fitmate.backend.dto.PortfolioDto;
import com.fitmate.backend.entity.*;
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
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final GradeRepository gradeRepository;
    private final MemberService memberService;
    private final StyleRepository styleRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;
    private final ReviewRepository reviewRepository;
    private final FollowRepository followRepository;
    private final TagRepository tagRepository;
    private final LikeRepository likeRepository;
    private final StyleCommentRepository styleCommentRepository;
    private final ChatRoomService chatRoomService;
    private static final Integer POST_PER_PAGE = 10;

    @Transactional
    public Portfolio makePortfolio(PortfolioDto portfolioDto){
        Member member = memberService.getMyInfo();
        Portfolio portfolio = PortfolioDto.toEntity(portfolioDto, member);
        portfolioRepository.save(PortfolioDto.toEntity(portfolioDto, member));
        Portfolio getPortfolio = portfolioRepository.findPortfolioByMemberId(member.getId()).orElseThrow(NotFoundUserInformation::new);
        Grade grade = GradeDto.toEntity(getPortfolio);
        gradeRepository.save(grade);
        chatRoomService.createChatRoom();
        return portfolio;
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
        List<Style> styleList = styleRepository.findAllByPortfolioIdOrderByIdDesc(portfolio.getId());
        for(Style style : styleList){
            tagRepository.deleteAllByStyle(style);
            likeRepository.deleteAllByStyle(style);
            styleCommentRepository.deleteAllByStyle(style);
            styleRepository.delete(style);
        }
        List<Reservation> reservationList = reservationRepository.findAllByPortfolioId(portfolio.getId());;
        for(Reservation reservation : reservationList){
            paymentRepository.deleteByReservation(reservation);
            reservationRepository.delete(reservation);
        }
        gradeRepository.deleteByStylist(portfolio);
        reviewRepository.deleteAllByPortfolio(portfolio);
        followRepository.deleteAllByStylist(portfolio);
        portfolioRepository.delete(portfolio);
        return "delete success";
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
