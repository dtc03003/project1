package com.fitmate.backend.repository;

import com.fitmate.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Long, Member> {

}
