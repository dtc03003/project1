package com.fitmate.backend.controller;

import com.fitmate.backend.dto.FollowDto;
import com.fitmate.backend.dto.GradeDto;
import com.fitmate.backend.dto.MemberDto;
import com.fitmate.backend.service.FollowService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "FollowController", description = "stylist 팔로우와 팔로우 취소 팔로우 목록 등 팔로우 관련 모든 정보 처리")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class FollowController {
    private final FollowService followService;

    @PostMapping(value = "/follow/{stylistNickname}")
    public ResponseEntity<String> follow(@PathVariable String stylistNickname){
        return ResponseEntity.ok(followService.follow(stylistNickname));
    }

    @DeleteMapping (value = "/cancelFollow/{stylistNickname}")
    public ResponseEntity<String> cancelFollow(@PathVariable String stylistNickname){
        return ResponseEntity.ok(followService.cancelFollow(stylistNickname));
    }

    @GetMapping(value = "/getFollowingList")
    public ResponseEntity<List<MemberDto>> getMyFollowing(){
        return ResponseEntity.ok(followService.getMyFollowing());
    }

    @GetMapping(value="/getFollowerList")
    public ResponseEntity<List<MemberDto>> getMyFollower(){
        return ResponseEntity.ok(followService.getMyFollower());
    }

    @GetMapping(value="/isFollowed/{stylistNickname}")
    public ResponseEntity<Boolean> isFollowed(@PathVariable String stylistNickname){
        return ResponseEntity.ok(followService.isFollowed(stylistNickname));
    }

    @GetMapping(value = "/countOfFollower/{stylistNickname}")
    public ResponseEntity<Integer> getCountOfFollower(@PathVariable String stylistNickname){
        return ResponseEntity.ok(followService.getCountOfFollower(stylistNickname));
    }
}
