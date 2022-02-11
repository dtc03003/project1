package com.fitmate.backend.controller;

import com.fitmate.backend.dto.ReviewDto;
import com.fitmate.backend.service.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@Tag(name="ReviewController" , description = "포트폴리오의 리뷰 관리")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping("/portfolio/{nickname}/review")
    public ResponseEntity<?> writeReview(@PathVariable String nickname, @RequestBody ReviewDto reviewDto){
        return ResponseEntity.ok(reviewService.writeReview(nickname, reviewDto));
    }
    @GetMapping("/review/{id}")
    public ResponseEntity<?> selectReviewById(@PathVariable Long id){
        return ResponseEntity.ok(reviewService.findById(id));
    }
    @GetMapping("/portfolio/{nickname}/reviews")
    public ResponseEntity<?> selectReviewsByPortfolioNickname(@RequestParam("page") @Min(0) Integer page, @PathVariable String nickname){
        return ResponseEntity.ok(reviewService.findAllReviewsByPortfolioNickname(page,nickname));
    }
    @GetMapping("/portfolio/{nickname}/reviews/all")
    public ResponseEntity<?> selectAllReviews(@PathVariable String nickname){
        return ResponseEntity.ok(reviewService.findAll(nickname));
    }
    @GetMapping("/members/{id}/reviews")
    public ResponseEntity<?> selectAllReviewsByMemberId(@PathVariable Long id){
        return ResponseEntity.ok(reviewService.findAllByMemberId(id));
    }
}
