package com.fitmate.backend.repository;

import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findPortfolioByMemberId(Long id);
}
