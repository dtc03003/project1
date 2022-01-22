package com.fitmate.backend.util.service;

import com.fitmate.backend.dto.MemberDto;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.repository.MemberRepository;
import com.fitmate.backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public List<Member> findMembers(){return memberRepository.findAll();}

    @Transactional
    public Member updateMember(MemberDto dto){
        Member member = getMemberByToken();
        member.updateMember(dto);
        return memberRepository.save(member);
    }
    @Transactional
    public Long deleteMember(){
        Member member = getMemberByToken();
        memberRepository.deleteById(member.getId());
        return member.getId();
    }

    @Transactional
    public Member getMyInfo() {
        return getMemberByToken();
    }

    private Member getMemberByToken(){
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }
}
