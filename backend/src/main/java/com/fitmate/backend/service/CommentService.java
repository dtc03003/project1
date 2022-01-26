package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundUserInformation;
import com.fitmate.backend.dto.CommentDto;
import com.fitmate.backend.dto.QnaDto;
import com.fitmate.backend.entity.Comment;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Qna;
import com.fitmate.backend.repository.CommentRepository;
import com.fitmate.backend.repository.MemberRepository;
import com.fitmate.backend.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final QnaRepository qnaRepository;

    @Transactional
    public CommentDto createComment(CommentDto commentDto){
        Member writer = memberRepository.findByNickname(commentDto.getWriter())
                .orElseThrow(NotFoundUserInformation::new);
        Qna qna = qnaRepository.getById(commentDto.getQnaId());
        return CommentDto.of(commentRepository.save(CommentDto.toEntity(commentDto,writer,qna)));
    }

    public CommentDto findComment(Long qnaId){
        Qna qna = qnaRepository.findById(qnaId).orElseThrow();
        return CommentDto.of(commentRepository.findByQna(qna).orElseThrow());
    }

    @Transactional
    public CommentDto updateComment(CommentDto commentDto){
        Comment comment = commentRepository.findById(commentDto.getId()).orElseThrow();
        comment.updateComment(commentDto);
        return CommentDto.of(commentRepository.save(comment));
    }

    @Transactional
    public String deleteComment(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow();
        commentRepository.delete(comment);
        return comment.getQna().getTitle();
    }
}
