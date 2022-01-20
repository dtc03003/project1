package com.fitmate.backend.service;

import com.fitmate.backend.entity.Member;
import com.fitmate.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long signup(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member){
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m -> {throw new IllegalStateException("The email already exists by email");});

        memberRepository.findByName(member.getName())
                .ifPresent(m -> {throw new IllegalStateException("The email already exists by name");});
    }

    public List<Member> findMembers(){return memberRepository.findAll();}
    public Optional<Member> findOne(Long memberNo){return memberRepository.findById(memberNo);}

}
