package com.fitmate.backend.service;

import com.fitmate.backend.dto.StyleCommentDto;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Portfolio;
import com.fitmate.backend.entity.Style;
import com.fitmate.backend.entity.StyleComment;
import com.fitmate.backend.repository.StyleCommentRepository;
import com.fitmate.backend.repository.StyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StyleCommentService {
    private final StyleCommentRepository styleCommentRepository;
    private final StyleService styleService;
    private final MemberService memberService;
    private static final Integer POST_PER_PAGE = 10;

    @Transactional
    public StyleComment writeComment(Long styleId, StyleCommentDto styleCommentDto){
        Member writer = memberService.getMyInfo();
        Style style = styleService.findById(styleId);
        return styleCommentRepository.save(StyleCommentDto.toEntity(styleCommentDto,writer,style));
    }

    public List<StyleComment> findAllCommentsByStyleId(Integer pageNum, Long id) {
        Page<StyleComment> page = styleCommentRepository.findAllByStyleId(
                PageRequest.of(pageNum-1, POST_PER_PAGE, Sort.by(Sort.Direction.DESC, "id")
                ),id);
        return page.stream()
                .map(StyleComment::new)
                .collect(Collectors.toList());
    }
    public List<StyleComment> getCommentsByStyleId(Long id) {
        return styleCommentRepository.findAllByStyleIdOrderByIdDesc(id);
    }

    public StyleComment getCommentByCommentId(Long id){
        return styleCommentRepository.findById(id).orElseThrow();
    }
}
