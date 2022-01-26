package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundUserInformation;
import com.fitmate.backend.dto.NoticeDto;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Notice;
import com.fitmate.backend.repository.MemberRepository;
import com.fitmate.backend.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

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

    public List<Notice> findNotice(PageRequest pageRequest){
        return noticeRepository.findAll(pageRequest).getContent();
    }

    public List<Notice> findNotice(){
        return noticeRepository.findAll();
    }

    public Notice findNoticeById(Long id){
        return noticeRepository.findById(id).orElseThrow();
    }

    @Transactional
    public String deleteNoticeById(Long id){
        Notice notice = this.findNoticeById(id);
        noticeRepository.delete(notice);
        return notice.getTitle();
    }

    @Transactional
    public Notice updateNotice(NoticeDto dto){
        Notice notice = this.findNoticeById(dto.getId());
        notice.updateNotice(dto);
        return noticeRepository.save(notice);
    }
}
