package com.fitmate.backend.repository;

import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Portfolio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findPortfolioByMemberId(Long id);
    Optional<Portfolio> findByMember_Nickname(String nickname);
    List<Portfolio> findByMember_NicknameContaining(String nickname);
    Page<Portfolio> findAll(Pageable pageable);
}
