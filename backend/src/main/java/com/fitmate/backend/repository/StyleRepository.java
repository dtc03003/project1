package com.fitmate.backend.repository;

import com.fitmate.backend.entity.Style;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StyleRepository extends JpaRepository<Style, Long> {
    List<Style> findAllByPortfolioIdOrderByIdDesc(Long id);
    Page<Style> findAll(Pageable pageable);
    Page<Style> findAllByPortfolioId(Pageable pageable, Long id);
}
