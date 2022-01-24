package com.fitmate.backend.repository;

import com.fitmate.backend.entity.Account;
import com.fitmate.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByMember(Member member);
}
