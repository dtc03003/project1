package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Authority;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.jwt.TokenProvider;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MemberDto {
    private String email;
    private String password;
    private String nickname;
    private String name;
    private int gender;
    private String phone;
    private int height;
    private int weight;
    private int top;
    private int bottom;
    private int shoeSize;
    private Authority authority;
    public static Member toEntity(MemberDto memberDto, PasswordEncoder passwordEncoder){
        return Member.builder()
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
    }
    public static MemberDto of(Member member){
        return MemberDto.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .name(member.getName())
                .phone(member.getPhone())
                .gender(member.getGender())
                .height(member.getHeight())
                .weight(member.getWeight())
                .top(member.getTop())
                .bottom(member.getBottom())
                .shoeSize(member.getShoeSize())
                .authority(member.getAuthority())
                .build();
    }
}
