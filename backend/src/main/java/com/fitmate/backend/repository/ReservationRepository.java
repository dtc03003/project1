package com.fitmate.backend.repository;

import com.fitmate.backend.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByPortfolioId(Long id);
}
