package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Authority;
import com.fitmate.backend.entity.Member;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class SocialMemberDto {
    private Long id;
    private Properties properties;
    private KaKaoAccount kakao_account;
    private String connected_at;
    @Getter
    @ToString
    public class KaKaoAccount{
        private String email;
        private String gender;
        private Profile profile;
        @Getter
        public class Profile{
            private String nickname;    //닉네임
        }
    }
    @Getter
    @ToString
    public class Properties{
        private String nickname;    // 성명
    }
    public static Member toEntity(SocialMemberDto socialMemberDto){
        return Member.builder()
                .email(socialMemberDto.getKakao_account().getEmail())   // 이메일
                .name(socialMemberDto.getProperties().getNickname())    // 성명
                .nickname(socialMemberDto.getKakao_account().getProfile().getNickname()+socialMemberDto.id) // 닉네임+회원번호
                .gender(socialMemberDto.getGenderValue(socialMemberDto.getKakao_account().getGender()))     // 성별
                .authority(Authority.ROLE_MEMBER)   // 일반회원만
                .build();
    }
    public int getGenderValue(String gender){
        switch (gender){
            case "male":
                return 0;
            case "female":
                return 1;
        }
        return -1;
    }
}
/*
                .email(memberDto.getEmail())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .nickname(memberDto.getNickname())
                .name(memberDto.getName())
                .phone(memberDto.getPhone())
                .gender(memberDto.getGender())
                .height(memberDto.getHeight())
                .weight(memberDto.getWeight())
                .top(memberDto.getTop())
                .bottom(memberDto.getBottom())
                .shoeSize(memberDto.getShoeSize())
                .authority(memberDto.getAuthority())
                .build();
 */

