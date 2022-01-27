package com.fitmate.backend.controller;

import com.fitmate.backend.advice.ApiError;
import com.fitmate.backend.advice.exception.NotFoundUserInformation;
import com.fitmate.backend.dto.MemberDto;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@Tag(name="MemberController" , description = "로그인된 사용자 정보 처리")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member")
    public ResponseEntity<Member> getMemberInfo(){
        return ResponseEntity.ok(memberService.getMyInfo());
    }
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

    @GetMapping(value = "/member/{id}")
    public ResponseEntity<Member> findMemberById(@PathVariable Long id){
        return ResponseEntity.ok(memberService.findMemberById(id));
    }
}
