package com.fitmate.backend.repository;

import com.fitmate.backend.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    Page<Review> findAllByPortfolioId(Pageable pageable, Long id);
    List<Review> findAllByPortfolioId(Long id);
}
