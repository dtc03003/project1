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

    @PostMapping(value = "/follow")
    public ResponseEntity<FollowDto> follow(@RequestBody FollowDto followDto){
        return ResponseEntity.ok(followService.follow(followDto));
    }

    @DeleteMapping (value = "/cancelFollow")
    public ResponseEntity<String> cancelFollow(@RequestBody FollowDto followDto){
        return ResponseEntity.ok(followService.cancelFollow(followDto));
    }

    @PutMapping (value="/updateGrade")
    public ResponseEntity<GradeDto> updateGrade(@RequestBody GradeDto gradeDto){
        return ResponseEntity.ok(followService.calculateGrade(gradeDto));
    }

    @GetMapping(value = "/getFollowingList/{memberNickname}")
    public ResponseEntity<List<MemberDto>> getMyFollowing(@PathVariable String memberNickname){
        return ResponseEntity.ok(followService.getMyFollowing(memberNickname));
    }

    @GetMapping(value="/getFollowerList/{stylistNickname}")
    public ResponseEntity<List<MemberDto>> getMyFollower(@PathVariable String stylistNickname){
        return ResponseEntity.ok(followService.getMyFollower(stylistNickname));
    }

    @GetMapping(value="/isFollowed")
    public ResponseEntity<Boolean> isFollowed(@RequestBody FollowDto followDto){
        return ResponseEntity.ok(followService.isFollowed(followDto));
    }
}
