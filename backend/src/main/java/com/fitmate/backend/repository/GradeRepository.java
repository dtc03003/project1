package com.fitmate.backend.repository;

import com.fitmate.backend.entity.Grade;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    Optional<Grade> findByStylist(Portfolio stylist);
    void deleteByStylist(Portfolio stylist);
}
