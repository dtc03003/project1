package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundUserInformation;
import com.fitmate.backend.dto.MemberDto;
import com.fitmate.backend.dto.MemberPasswordDto;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Qna;
import com.fitmate.backend.entity.Reservation;
import com.fitmate.backend.entity.StyleComment;
import com.fitmate.backend.repository.*;
import com.fitmate.backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final QnaRepository qnaRepository;
    private final QnaService qnaService;
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final FollowRepository followRepository;
    private final ReservationService reservationService;
    private final LikeRepository likeRepository;
    private final StyleCommentRepository styleCommentRepository;

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
        List<Qna> qnaList = qnaRepository.findAllByWriter(member);
        for(Qna qna : qnaList){
            qnaService.deleteQnaById(qna.getId());
        }
        commentRepository.deleteAllByWriter(member);
        reviewRepository.deleteAllByWriter(member);
        followRepository.deleteAllByFollower(member);
        styleCommentRepository.deleteAllByMember(member);
        likeRepository.deleteAllByMember(member);
        List<Reservation> reservationList = reservationService.findAllReservationsByNickname(member.getNickname());
        for(Reservation reservation : reservationList){
            reservationService.deleteReservation(reservation.getId());
        }
        memberRepository.deleteById(member.getId());
        return member.getId();
    }

    @Transactional
    public Member getMyInfo() {
        return getMemberByToken();
    }

    private Member getMemberByToken(){
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(NotFoundUserInformation::new);
    }

    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(NotFoundUserInformation::new);
    }
    public Member updatePassword(MemberPasswordDto memberPasswordDto) {
        Member member = getMyInfo();
        System.out.println(memberPasswordDto.getPassword());
        member.updatePassword(memberPasswordDto.getPassword(),passwordEncoder);
        return memberRepository.save(member);
    }
}
