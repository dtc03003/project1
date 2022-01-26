package com.fitmate.backend.controller;

import com.fitmate.backend.entity.Account;
import com.fitmate.backend.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name="AccountController" , description = "계좌 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/accountList")
    public List<Account> getMyAccountList(){
        return accountService.findAllByMyInfo();
    }
    @PostMapping("/account")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account){
        return ResponseEntity.ok(accountService.registerAccount(account));
    }
    @DeleteMapping("/account/{id}")
    public ResponseEntity<Long> deleteAccount(@PathVariable Long id){
        return ResponseEntity.ok(accountService.deleteAccount(id));
    }
}
