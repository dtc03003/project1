package com.fitmate.backend.controller;

import com.fitmate.backend.dto.MemberDto;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/member")
    public ResponseEntity<Long> signup(@RequestBody Member member){
        return ResponseEntity.ok(memberService.signup(member));

    }
//    @GetMapping("/me")
//    public ResponseEntity<Member> getMyMemberInfo(){
//        return ResponseEntity.ok(memberService.getMyInfo());
//    }

    @GetMapping("/member/{email}")
    public ResponseEntity<Member> getMemberInfo(@PathVariable String email){
        return ResponseEntity.ok(memberService.findByEmail(email));
    }

    @Secured("ROLE_MEMBER")
    @GetMapping("/members")
    public ResponseEntity<List<Member>> findMembers(){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(memberService.findMembers());
    }

//    @GetMapping("/member/{id}")
//    public ResponseEntity<Member> findMember(@PathVariable Long id){
//        return ResponseEntity.ok(memberService.findOne(id));
//    }

    @PutMapping(value = "/member/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody MemberDto memberDto){
        return ResponseEntity.ok(memberService.updateMember(id,memberDto));
    }
    @DeleteMapping(value = "/member/{id}")
    public ResponseEntity<Long> deleteMember(@PathVariable Long id){
        return ResponseEntity.ok(memberService.deleteMember(id));
    }
    @GetMapping("/me")
    public ResponseEntity<Member> getMyMemberInfo() {
        return ResponseEntity.ok(memberService.getMyInfo());
    }
}
