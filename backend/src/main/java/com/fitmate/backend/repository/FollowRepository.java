package com.fitmate.backend.repository;

import com.fitmate.backend.entity.Follow;
import com.fitmate.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByMemberAndStylist(Member member, Member stylist);
    Optional<List<Follow>> findAllByMember(Member member);
    Optional<List<Follow>> findAllByStylist(Member stylist);
}
