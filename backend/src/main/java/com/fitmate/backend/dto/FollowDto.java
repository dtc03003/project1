package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Follow;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Portfolio;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FollowDto {
    private String followingNickName;
    private String followerNickName;

    public static Follow toEntity(Member member, Portfolio stylist){
        return Follow.builder()
                .member(member)
                .stylist(stylist)
                .build();
    }

    public static FollowDto of(Follow follow){
        return FollowDto.builder()
                .followerNickName(follow.getMember().getNickname())
                .followingNickName(follow.getStylist().getMember().getNickname())
                .build();
    }
}
