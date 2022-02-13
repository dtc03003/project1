package com.fitmate.backend.repository;

import com.fitmate.backend.entity.LikeEntity;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Style;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByMemberAndStyle(Member member, Style style);
    int countByStyle(Style style);
    int countByMemberAndStyle(Member member, Style style);
    List<LikeEntity> findAllByStyle(Style style);
    List<LikeEntity> findAllByMember(Member member);
    void deleteAllByStyle(Style style);
    void deleteAllByMember(Member member);
}
