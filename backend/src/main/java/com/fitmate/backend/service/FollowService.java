package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundPortfolioException;
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
    private final PortfolioRepository portfolioRepository;
    private final GradeRepository gradeRepository;
    private final MemberService memberService;

    @Transactional
    public String follow(String stylistNickname){
        if(isFollowed(stylistNickname)) return "already followed";
        else {
            Member follower = memberService.getMyInfo();
            Portfolio following = portfolioRepository.findByMember_Nickname(stylistNickname).orElseThrow(NotFoundPortfolioException::new);
            Follow follow = FollowDto.toEntity(follower, following);
            Grade grade = gradeRepository.findByStylist(following).orElseThrow();
            grade.increaseFollower();
            gradeRepository.save(grade);
            followRepository.save(follow);
            return "follow success";
        }
    }

    @Transactional
    public String cancelFollow(String stylistNickname){
        Member follower = memberService.getMyInfo();
        Portfolio following = portfolioRepository.findByMember_Nickname(stylistNickname).orElseThrow(NotFoundPortfolioException::new);
        Follow follow = followRepository.findByMemberAndStylist(follower, following).orElseThrow();
        Grade grade = gradeRepository.findByStylist(following).orElseThrow();
        grade.decreaseFollower();
        gradeRepository.save(grade);
        followRepository.delete(follow);
        return following.getMember().getNickname();
    }

    public List<MemberDto> getMyFollowing(){
        Member member = memberService.getMyInfo();
        List<Follow> followingList = followRepository.findAllByMember(member);
        List<MemberDto> resultList = new ArrayList<MemberDto>();
        for(int i=followingList.size()-1; i>=0; i--){
            resultList.add(MemberDto.of(followingList.get(i).getStylist().getMember()));
        }
        return resultList;
    }

    public List<MemberDto> getMyFollower(){
        Member stylistMember = memberService.getMyInfo();
        Portfolio stylist = portfolioRepository.findByMember_Nickname(stylistMember.getNickname()).orElseThrow(NotFoundPortfolioException::new);
        List<Follow> followerList = followRepository.findAllByStylist(stylist);
        List<MemberDto> resultList = new ArrayList<MemberDto>();
        for(int i=0; i<followerList.size(); i++){
            resultList.add(MemberDto.of(followerList.get(i).getMember()));
        }
        return resultList;
    }

    public boolean isFollowed(String stylistNickname){
        Member member = memberService.getMyInfo();
        Portfolio stylist = portfolioRepository.findByMember_Nickname(stylistNickname).orElseThrow(NotFoundPortfolioException::new);
        int result = followRepository.countByMemberAndStylist(member, stylist);
        return result==0?false:true;
    }

    public int getCountOfFollower(String stylistNickname){
        Portfolio stylist = portfolioRepository.findByMember_Nickname(stylistNickname).orElseThrow(NotFoundPortfolioException::new);
        return followRepository.countByStylist(stylist);
    }
}
