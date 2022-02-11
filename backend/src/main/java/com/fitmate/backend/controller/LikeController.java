package com.fitmate.backend.controller;

import com.fitmate.backend.dto.MemberDto;
import com.fitmate.backend.entity.Style;
import com.fitmate.backend.service.LikeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "LikeController", description = "style과 member의 좋아요 관련 모든 정보 처리")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/like/{styleId}")
    public ResponseEntity<String> like(@PathVariable Long styleId){
        return ResponseEntity.ok(likeService.like(styleId));
    }

    @DeleteMapping("/like/{styleId}")
    public ResponseEntity<String> cancelLike(@PathVariable Long styleId){
        return ResponseEntity.ok(likeService.cancelLike(styleId));
    }

    @GetMapping("/like/{styleId}/count")
    public ResponseEntity<Integer> countOfLike(@PathVariable Long styleId){
        return ResponseEntity.ok(likeService.likeCount(styleId));
    }

    @GetMapping("/like/isLiked/{styleId}")
    public ResponseEntity<Boolean> isLiked(@PathVariable Long styleId){
        return ResponseEntity.ok(likeService.isLiked(styleId));
    }

    @GetMapping("/like/{styleId}/liker")
    public ResponseEntity<List<MemberDto>> getLikers(@PathVariable Long styleId){
        return ResponseEntity.ok(likeService.getLiker(styleId));
    }

    @GetMapping("/member/me/likes")
    public ResponseEntity<List<Style>> getMyLikeStyle(){
        return ResponseEntity.ok(likeService.getMyLikeStyle());
    }
}
