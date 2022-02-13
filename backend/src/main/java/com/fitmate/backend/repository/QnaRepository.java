package com.fitmate.backend.repository;

import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {
    void deleteAllByMember(Member writer);
    List<Qna> findAllByMember(Member writer);
}
