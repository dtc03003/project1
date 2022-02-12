package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundUserInformation;
import com.fitmate.backend.dto.QnaDto;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Qna;
import com.fitmate.backend.repository.CommentRepository;
import com.fitmate.backend.repository.MemberRepository;
import com.fitmate.backend.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QnaRepository qnaRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public QnaDto createQna(QnaDto qnaDto){
        Member writer = memberRepository.findByNickname(qnaDto.getWriter()).orElseThrow(NotFoundUserInformation::new);
        Qna qna = QnaDto.toEntity(qnaDto, writer);
        QnaDto result = QnaDto.of(qnaRepository.save(qna));
        return result;
    }

    public List<QnaDto> findQna(PageRequest pageRequest){
        List<Qna> qnaList = qnaRepository.findAll(pageRequest).getContent();
        List<QnaDto> qnaDtoList = new ArrayList<QnaDto>();
        for(int i=0; i<qnaList.size(); i++){
            qnaDtoList.add(QnaDto.of(qnaList.get(i)));
        }
        return qnaDtoList;
    }

    public List<QnaDto> findQna() {
        List<Qna> qnaList = qnaRepository.findAll(Sort.by("createdAt").descending());
        List<QnaDto> qnaDtoList = new ArrayList<QnaDto>();
        for(int i=0; i<qnaList.size(); i++){
            qnaDtoList.add(QnaDto.of(qnaList.get(i)));
        }
        return qnaDtoList;
    }

    public QnaDto findQnaById(Long id) {
        return QnaDto.of(qnaRepository.findById(id).orElseThrow());
    }

    @Transactional
    public String deleteQnaById(Long id){
        Qna qna = qnaRepository.findById(id).orElseThrow();
        commentRepository.deleteAllByQna(qna);
        qnaRepository.delete(qna);
        return "delete success";
    }

    @Transactional
    public QnaDto updateQna(QnaDto qnaDto){
        Qna qna = qnaRepository.findById(qnaDto.getId()).orElseThrow();
        qna.updateQna(qnaDto);
        return QnaDto.of(qnaRepository.save(qna));
    }
}
