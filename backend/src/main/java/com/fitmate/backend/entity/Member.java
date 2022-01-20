package com.fitmate.backend.entity;

import com.fitmate.backend.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberNo")
    private Long id;
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
    @Column(name="image_imageNo")
    private Long imageNo;

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
        this.imageNo = dto.getImageNo();
        return this;
    }
}
