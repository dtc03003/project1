package com.fitmate.backend.repository;

import com.fitmate.backend.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StyleCommentRepository extends JpaRepository<StyleComment, Long> {
    Optional<StyleComment> findByStyle(Style style);
    Page<StyleComment> findAllByStyleId(Pageable pageable, Long id);
    List<StyleComment> findAllByStyleIdOrderByIdDesc(Long id);
    void deleteAllByStyle(Style style);
    void deleteAllByMember(Member member);
}
