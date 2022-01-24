package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundUserInformation;
import com.fitmate.backend.dto.NoticeDto;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Notice;
import com.fitmate.backend.repository.MemberRepository;
import com.fitmate.backend.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Notice createNotice(NoticeDto dto){
        Member writer = memberRepository.findByNickname(dto.getWriter()).orElseThrow(NotFoundUserInformation::new);
        Notice notice = NoticeDto.toEntity(dto, writer);
        return noticeRepository.save(notice);
    }
}
