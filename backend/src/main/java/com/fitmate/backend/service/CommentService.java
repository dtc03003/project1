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
import java.util.ArrayList;
import java.util.List;

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

    public List<CommentDto> findComment(Long qnaId){
        Qna qna = qnaRepository.findById(qnaId).orElseThrow();
        List<Comment> lists = commentRepository.findAllByQna(qna);
        List<CommentDto> result = new ArrayList<CommentDto>();
        for(int i=0; i<lists.size(); i++){
            result.add(CommentDto.of(lists.get(i)));
        }
        return result;
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
