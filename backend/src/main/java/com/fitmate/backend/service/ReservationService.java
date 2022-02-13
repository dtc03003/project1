package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundReservationException;
import com.fitmate.backend.advice.exception.UpdateStateException;
import com.fitmate.backend.dto.ReservationDateTimeDto;
import com.fitmate.backend.dto.ReservationDto;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Portfolio;
import com.fitmate.backend.entity.Reservation;
import com.fitmate.backend.entity.State;
import com.fitmate.backend.repository.PaymentRepository;
import com.fitmate.backend.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberService memberService;
    private final PortfolioService portfolioService;
    private final PaymentRepository paymentRepository;

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
    public List<Reservation> findAllReservationsByNicknameInAllState(String nickname, Pageable pageable) {
        return reservationRepository.findAll(pageable).getContent().stream()
                .filter(reservation -> reservation.getPortfolio().getNickname().equals(nickname))
                .collect(Collectors.toList());
    }

    public List<Reservation> findAllReservationsByNicknameInState(String nickname, Pageable pageable, String state) {
        if(state==null) return findAllReservationsByNicknameInAllState(nickname, pageable);
        return reservationRepository.findAll(pageable).getContent().stream()
                .filter(reservation -> reservation.getPortfolio().getNickname().equals(nickname))
                .filter(reservation -> reservation.getState().name().equals(state))
                .collect(Collectors.toList());
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
    public String deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(NotFoundReservationException::new);
        paymentRepository.deleteByReservation(reservation);
        reservationRepository.deleteById(id);
        return "delete success";
    }

    @Transactional
    public Reservation cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(NotFoundReservationException::new);
        if(reservation.getState()!=State.IN_PROGRESS) throw new UpdateStateException();
        reservation.cancel();
        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation completeReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(NotFoundReservationException::new);
        if(reservation.getState()!=State.IN_PROGRESS) throw new UpdateStateException();
        reservation.complete();
        return reservationRepository.save(reservation);
    }

    public List<Reservation> findAllAtCurrentTime(LocalDateTime now){
        List<Reservation> reservations = reservationRepository.findAllByState(State.IN_PROGRESS);
        return reservations.stream()
                .filter(reservation -> reservation.getStartTime().isBefore(now))
                .collect(Collectors.toList());
    }
}
