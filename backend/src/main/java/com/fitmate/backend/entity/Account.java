package com.fitmate.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="account_info")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Account {
    @Id
    @Column(name = "no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String accountNumber;
    private String accountHolder;
    @ManyToOne
    @JoinColumn(name="member_no")
    private Member member;
    private String bankName;

    public Account updateMemberInfo(Member member){
        this.member = member;
        return this;
    }
}
