package com.fitmate.backend.service;

import com.fitmate.backend.entity.Account;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final MemberService memberService;

    public List<Account> findAllByMyInfo(){
        Member member = memberService.getMyInfo();
        return accountRepository.findAllByMember(member);
    }
    @Transactional
    public Account registerAccount(Account account){
        Member member = memberService.getMyInfo();
        account.updateMemberInfo(member);
        return accountRepository.save(account);
    }
    @Transactional
    public Long deleteAccount(Long id){
        accountRepository.deleteById(id);
        return id;
    }
}
