package com.fitmate.backend.controller;

import com.fitmate.backend.dto.MemberDto;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.util.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member")
    public ResponseEntity<Member> getMemberInfo(){
        return ResponseEntity.ok(memberService.getMyInfo());
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/members")
    public ResponseEntity<List<Member>> findMembers(){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(memberService.findMembers());
    }
    @PutMapping(value = "/member")
    public ResponseEntity<Member> updateMember(@RequestBody MemberDto memberDto){
        return ResponseEntity.ok(memberService.updateMember(memberDto));
    }
    @DeleteMapping(value = "/member")
    public ResponseEntity<Long> deleteMember(){
        return ResponseEntity.ok(memberService.deleteMember());
    }
}
