package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundUserInformation;
import com.fitmate.backend.dto.FollowDto;
import com.fitmate.backend.dto.GradeDto;
import com.fitmate.backend.dto.MemberDto;
import com.fitmate.backend.dto.PortfolioDto;
import com.fitmate.backend.entity.Follow;
import com.fitmate.backend.entity.Grade;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Portfolio;
import com.fitmate.backend.repository.FollowRepository;
import com.fitmate.backend.repository.GradeRepository;
import com.fitmate.backend.repository.MemberRepository;
import com.fitmate.backend.repository.PortfolioRepository;
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
    private final PortfolioRepository portfolioRepository;
    private final GradeRepository gradeRepository;

    @Transactional
    public FollowDto follow(FollowDto followDto){
        Member follower = memberRepository.findByNickname(followDto.getFollowerNickName()).orElseThrow(NotFoundUserInformation::new);
        Portfolio following = portfolioRepository.findByMember_Nickname(followDto.getFollowingNickName()).orElseThrow(NotFoundUserInformation::new);
        Follow follow = FollowDto.toEntity(follower, following);
        Grade grade = gradeRepository.findByStylist(following).orElseThrow();
        grade.increasingFollow();
        gradeRepository.save(grade);
        return FollowDto.of(followRepository.save(follow));
    }

    @Transactional
    public String cancelFollow(FollowDto followDto){
        Member follower = memberRepository.findByNickname(followDto.getFollowerNickName()).orElseThrow(NotFoundUserInformation::new);
        Portfolio following = portfolioRepository.findByMember_Nickname(followDto.getFollowingNickName()).orElseThrow(NotFoundUserInformation::new);
        Follow follow = followRepository.findByMemberAndStylist(follower, following).orElseThrow();
        Grade grade = gradeRepository.findByStylist(following).orElseThrow();
        grade.decreasingFollow();
        gradeRepository.save(grade);
        followRepository.delete(follow);
        return following.getMember().getNickname();
    }

    @Transactional
    public GradeDto calculateGrade(GradeDto gradeDto){
        Portfolio stylist = portfolioRepository.findByMember_Nickname(gradeDto.getStylistNickName()).orElseThrow(NotFoundUserInformation::new);
        Grade grade = gradeRepository.findByStylist(stylist).orElseThrow(NotFoundUserInformation::new);
        grade.updateGrade(gradeDto.getGrade());
        return GradeDto.of(grade);
    }

    public List<MemberDto> getMyFollowing(String memberNickname){
        Member member = memberRepository.findByNickname(memberNickname).orElseThrow(NotFoundUserInformation::new);
        List<Follow> followingList = followRepository.findAllByMember(member).orElseThrow();
        List<MemberDto> resultList = new ArrayList<MemberDto>();
        for(int i=0; i<followingList.size(); i++){
            resultList.add(MemberDto.of(followingList.get(i).getStylist().getMember()));
        }
        return resultList;
    }

    public List<MemberDto> getMyFollower(String stylistNickname){
        Portfolio stylist = portfolioRepository.findByMember_Nickname(stylistNickname).orElseThrow(NotFoundUserInformation::new);
        List<Follow> followerList = followRepository.findAllByStylist(stylist).orElseThrow();
        List<MemberDto> resultList = new ArrayList<MemberDto>();
        for(int i=0; i<followerList.size(); i++){
            resultList.add(MemberDto.of(followerList.get(i).getStylist().getMember()));
        }
        return resultList;
    }

    public boolean isFollowed(FollowDto followDto){
        Member member = memberRepository.findByNickname(followDto.getFollowerNickName()).orElseThrow(NotFoundUserInformation::new);
        Portfolio stylist = portfolioRepository.findByMember_Nickname(followDto.getFollowingNickName()).orElseThrow(NotFoundUserInformation::new);
        boolean result = followRepository.countByMemberAndStylist(member, stylist);
        return result;
    }
}
