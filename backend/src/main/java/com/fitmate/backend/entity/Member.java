package com.fitmate.backend.entity;

import com.fitmate.backend.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
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
    private String profile;
    @Column(nullable = false)
    private String social;

    @PrePersist
    public void social(){
        this.social = this.social == null ? "normal" : this.social;
    }

    public void updateMember(MemberDto dto){
        this.nickname = dto.getNickname();
        this.name =dto.getName();
        this.gender= dto.getGender();
        this.phone =dto.getPhone();
        this.height = dto.getHeight();
        this.weight = dto.getWeight();
        this.top = dto.getTop();
        this.bottom = dto.getBottom();
        this.shoeSize = dto.getShoeSize();
        this.profile = dto.getProfile();
    }
    public void updatePassword(String password, PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }
}
