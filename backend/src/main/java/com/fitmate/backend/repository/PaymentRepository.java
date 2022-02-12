package com.fitmate.backend.repository;

import com.fitmate.backend.entity.Payment;
import com.fitmate.backend.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    void deleteByReservation(Reservation reservation);
}
