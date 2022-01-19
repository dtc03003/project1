package com.fitmate.backend.entity;

import com.fitmate.backend.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="member")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Member{
    @Id
    @Column(name = "memberNo")
    private Long id;
    @Column(name ="name")
    private String name;
    public Member updateMember(MemberDto dto){
        this.name = dto.getName();
        return this;
    }
}
