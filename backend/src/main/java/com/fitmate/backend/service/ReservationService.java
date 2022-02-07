package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundReservationException;
import com.fitmate.backend.advice.exception.UpdateStateException;
import com.fitmate.backend.dto.ReservationDateTimeDto;
import com.fitmate.backend.dto.ReservationDto;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Portfolio;
import com.fitmate.backend.entity.Reservation;
import com.fitmate.backend.entity.State;
import com.fitmate.backend.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
            if(reservation.getState()!= State.IN_PROGRESS) continue;
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

    @Transactional
    public Reservation cancelReservation(String nickname, Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(NotFoundReservationException::new);
        if(reservation.getState()!=State.IN_PROGRESS) throw new UpdateStateException();
        reservation.cancel();
        return reservation;
    }

    @Transactional
    public Reservation completeReservation(String nickname, Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(NotFoundReservationException::new);
        if(reservation.getState()!=State.IN_PROGRESS) throw new UpdateStateException();
        reservation.complete();
        return reservation;
    }

    public List<Reservation> findAllReservationByNicknameInState(String nickname, String state) {
        Portfolio portfolio = portfolioService.getPortfolioByNickname(nickname);
        return reservationRepository.findAllByPortfolioId(portfolio.getId())
                .stream()
                .filter(reservation -> reservation.getState().equals(State.valueOf(state)))
                .collect(Collectors.toList());
    }
}
