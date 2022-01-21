package com.fitmate.backend.entity;

import com.fitmate.backend.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Member implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int gender;
    private String phone;
    private int height;
    private int weight;
    private int top;
    private int bottom;
    private int shoeSize;

    @Enumerated(EnumType.STRING)
    private Authority authority;


    public Member updateMember(MemberDto dto){
        this.password = dto.getPassword();
        this.nickname = dto.getNickname();
        this.name =dto.getName();
        this.gender= dto.getGender();
        this.phone =dto.getPhone();
        this.height = dto.getHeight();
        this.weight = dto.getWeight();
        this.top = dto.getTop();
        this.bottom = dto.getBottom();
        this.shoeSize = dto.getShoeSize();
        return this;
    }
    public Member encodingPassword(String password){
        this.password = password;
        return this;
    }
}
