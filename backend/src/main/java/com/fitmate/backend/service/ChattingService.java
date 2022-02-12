package com.fitmate.backend.service;

import com.fitmate.backend.dto.ChattingDto;
import com.fitmate.backend.entity.Chatting;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.repository.ChattingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChattingService {
    private final MemberService memberService;
    private final ChattingRepository chattingRepository;
    public Chatting chattingHandler(ChattingDto chattingDto){
        Member member = memberService.getMyInfo();
        return chattingRepository.save(ChattingDto.toEntity(chattingDto,member));
    }
}
