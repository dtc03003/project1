package com.fitmate.backend.controller;

import com.fitmate.backend.entity.Member;
import com.fitmate.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/member")
    public ResponseEntity<Long> signup(@RequestBody Member member){
        return ResponseEntity.ok(memberService.signup(member));

    }
    @GetMapping("/members")
    public ResponseEntity<List<Member>> findMembers(){
        return ResponseEntity.ok(memberService.findMembers());
    }
}
