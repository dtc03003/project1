package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundStyleException;
import com.fitmate.backend.dto.LikeDto;
import com.fitmate.backend.dto.MemberDto;
import com.fitmate.backend.entity.LikeEntity;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Style;
import com.fitmate.backend.repository.LikeRepository;
import com.fitmate.backend.repository.StyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final MemberService memberService;
    private final StyleRepository styleRepository;

    @Transactional
    public String like(Long styleId){
        if(isLiked(styleId))
            return "already like";
        else {
            Member member = memberService.getMyInfo();
            Style style = styleRepository.findById(styleId).orElseThrow(NotFoundStyleException::new);
            LikeEntity like = LikeDto.toEntity(member, style);
            likeRepository.save(like);
            return "like success";
        }
    }

    @Transactional
    public String cancelLike(Long styleId){
        Member member = memberService.getMyInfo();
        Style style = styleRepository.findById(styleId).orElseThrow(NotFoundStyleException::new);
        LikeEntity like = likeRepository.findByMemberAndStyle(member, style).orElseThrow();
        likeRepository.delete(like);
        return "cancel success";
    }

    public int likeCount(Long styleId){
        return likeRepository.countByStyle(styleRepository.findById(styleId).orElseThrow(NotFoundStyleException::new));
    }

    public boolean isLiked(Long styleId){
        int result = likeRepository.countByMemberAndStyle(memberService.getMyInfo(), styleRepository.findById(styleId).orElseThrow(NotFoundStyleException::new));
        return result==0?false:true;
    }

    public List<MemberDto> getLiker(Long styleId){
        List<LikeEntity> likes = likeRepository.findAllByStyle(styleRepository.findById(styleId).orElseThrow(NotFoundStyleException::new));
        List<MemberDto> memberDtoList = new ArrayList<MemberDto>();
        for(int i=0; i<likes.size(); i++){
            memberDtoList.add(MemberDto.of(likes.get(i).getMember()));
        }
        return memberDtoList;
    }

    public List<Style> getMyLikeStyle(){
        List<LikeEntity> likes = likeRepository.findAllByMember(memberService.getMyInfo());
        List<Style> styleList = new ArrayList<Style>();
        for(int i=likes.size()-1; i>=0; i--){
            styleList.add(likes.get(i).getStyle());
        }
        return styleList;
    }
}
