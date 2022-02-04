package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundUserInformation;
import com.fitmate.backend.dto.FollowDto;
import com.fitmate.backend.dto.GradeDto;
import com.fitmate.backend.dto.MemberDto;
import com.fitmate.backend.entity.Follow;
import com.fitmate.backend.entity.Grade;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.repository.FollowRepository;
import com.fitmate.backend.repository.GradeRepository;
import com.fitmate.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final GradeRepository gradeRepository;

    @Transactional
    public FollowDto follow(FollowDto followDto){
        Member follower = memberRepository.findByNickname(followDto.getFollowerNickName()).orElseThrow(NotFoundUserInformation::new);
        Member following = memberRepository.findByNickname(followDto.getFollowingNickName()).orElseThrow(NotFoundUserInformation::new);
        Follow follow = FollowDto.toEntity(follower, following);
        Grade grade = gradeRepository.findByStylist(following).orElseThrow();
        grade.increasingFollow();
        gradeRepository.save(grade);
        return FollowDto.of(followRepository.save(follow));
    }

    @Transactional
    public String followCancel(FollowDto followDto){
        Member follower = memberRepository.findByNickname(followDto.getFollowerNickName()).orElseThrow(NotFoundUserInformation::new);
        Member following = memberRepository.findByNickname(followDto.getFollowingNickName()).orElseThrow(NotFoundUserInformation::new);
        Follow follow = followRepository.findByMemberAndStylist(follower, following).orElseThrow();
        Grade grade = gradeRepository.findByStylist(following).orElseThrow();
        grade.decreasingFollow();
        gradeRepository.save(grade);
        followRepository.delete(follow);
        return following.getNickname();
    }

    @Transactional
    public GradeDto calculateGrade(GradeDto gradeDto){
        Member stylist = memberRepository.findByNickname(gradeDto.getStylistNickName()).orElseThrow(NotFoundUserInformation::new);
        Grade grade = gradeRepository.findByStylist(stylist).orElseThrow(NotFoundUserInformation::new);
        grade.updateGrade(gradeDto.getGrade());
        return GradeDto.of(grade);
    }

    public List<MemberDto> getMyFollowing(MemberDto memberDto){
        Member member = memberRepository.findByNickname(memberDto.getNickname()).orElseThrow(NotFoundUserInformation::new);
        List<Follow> followingList = followRepository.findAllByMember(member).orElseThrow();
        List<MemberDto> resultList = new ArrayList<MemberDto>();
        for(int i=0; i<followingList.size(); i++){
            resultList.add(MemberDto.of(followingList.get(i).getStylist()));
        }
        return resultList;
    }

    public List<MemberDto> getMyFollower(MemberDto memberDto){
        Member member = memberRepository.findByNickname(memberDto.getNickname()).orElseThrow(NotFoundUserInformation::new);
        List<Follow> followerList = followRepository.findAllByStylist(member).orElseThrow();
        List<MemberDto> resultList = new ArrayList<MemberDto>();
        for(int i=0; i<followerList.size(); i++){
            resultList.add(MemberDto.of(followerList.get(i).getStylist()));
        }
        return resultList;
    }

    public boolean isFollowed(MemberDto memberDto, Member stylistDto){
        Member member = memberRepository.findByNickname(memberDto.getNickname()).orElseThrow(NotFoundUserInformation::new);
        Member stylist = memberRepository.findByNickname(stylistDto.getNickname()).orElseThrow(NotFoundUserInformation::new);
        boolean result = followRepository.countByMemberAndStylist(member, stylist);
        return result;
    }
}
