package com.fitmate.backend.repository;

import com.fitmate.backend.entity.Comment;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByQna(Qna qna);
    List<Comment> findAllByQna(Qna qna);
    void deleteAllByQna(Qna qna);
    void deleteAllByMember(Member writer);
}
