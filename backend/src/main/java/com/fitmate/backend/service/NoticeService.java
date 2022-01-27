package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundUserInformation;
import com.fitmate.backend.dto.NoticeDto;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Notice;
import com.fitmate.backend.repository.MemberRepository;
import com.fitmate.backend.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public NoticeDto createNotice(NoticeDto dto){
        Member writer = memberRepository.findByNickname(dto.getWriter()).orElseThrow(NotFoundUserInformation::new);
        Notice notice = NoticeDto.toEntity(dto, writer);
        return NoticeDto.of(noticeRepository.save(notice));
    }

    public List<NoticeDto> findNotice(PageRequest pageRequest){
        List<Notice> noticeList = noticeRepository.findAll(pageRequest).getContent();
        List<NoticeDto> result = new ArrayList<NoticeDto>();
        for(int i=0; i<noticeList.size(); i++){
            result.add(NoticeDto.of(noticeList.get(i)));
        }
        return result;
    }

    public List<NoticeDto> findNotice(){
        List<Notice> noticeList = noticeRepository.findAll(Sort.by("createdAt").descending());
        List<NoticeDto> result = new ArrayList<NoticeDto>();
        for(int i=0; i<noticeList.size(); i++){
            result.add(NoticeDto.of(noticeList.get(i)));
        }
        return result;
    }

    public NoticeDto findNoticeById(Long id){
        return NoticeDto.of(noticeRepository.findById(id).orElseThrow());
    }

    @Transactional
    public String deleteNoticeById(Long id){
        Notice notice = noticeRepository.findById(id).orElseThrow();
        noticeRepository.delete(notice);
        return notice.getTitle();
    }

    @Transactional
    public NoticeDto updateNotice(NoticeDto dto){
        Notice notice = noticeRepository.findById(dto.getId()).orElseThrow();
        notice.updateNotice(dto);
        return NoticeDto.of(noticeRepository.save(notice));
    }
}
