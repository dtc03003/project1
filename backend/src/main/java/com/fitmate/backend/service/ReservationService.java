package com.fitmate.backend.service;

import com.fitmate.backend.dto.ReservationDateTimeDto;
import com.fitmate.backend.dto.ReservationDto;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Portfolio;
import com.fitmate.backend.entity.Reservation;
import com.fitmate.backend.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberService memberService;
    private final PortfolioService portfolioService;

    @Transactional
    public Reservation makeReservation(String nickname, ReservationDto reservationDto) {
        Member member = memberService.getMyInfo();
        Portfolio portfolio = portfolioService.getPortfolioByNickname(nickname);
        return reservationRepository.save(ReservationDto.toEntity(reservationDto,member,portfolio));
    }

    public List<Reservation> findAllReservationsByNickname(String nickname) {
        Portfolio portfolio = portfolioService.getPortfolioByNickname(nickname);
        return reservationRepository.findAllByPortfolioId(portfolio.getId());
    }

    public List<ReservationDateTimeDto> findAllTimeByNickname(String nickname) {
        List<Reservation> reservations = findAllReservationsByNickname(nickname);
        List<ReservationDateTimeDto> reservationDateTimeDtos = new LinkedList<>();
        for (Reservation reservation: reservations) {
            Long hours = reservation.getBetween();
            for(int i = 0 ; i < hours; i++){
                reservationDateTimeDtos.add(ReservationDateTimeDto.builder()
                        .localDate(reservation.getStartTime().toLocalDate())
                        .hours(reservation.getHour()+i)
                        .build());
            }
        }
        return reservationDateTimeDtos;
    }

    @Transactional
    public Long deleteReservation(String nickname, Long id) {
        Portfolio portfolio = portfolioService.getPortfolioByNickname(nickname);
        reservationRepository.deleteById(id);
        return portfolio.getId();
    }
}
