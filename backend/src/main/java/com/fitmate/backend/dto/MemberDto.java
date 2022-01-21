package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MemberDto {
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
}
