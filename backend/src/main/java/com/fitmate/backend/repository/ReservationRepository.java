package com.fitmate.backend.repository;

import com.fitmate.backend.entity.Portfolio;
import com.fitmate.backend.entity.Reservation;
import com.fitmate.backend.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByPortfolioId(Long id);
    List<Reservation> findAllByState(State state);
}
