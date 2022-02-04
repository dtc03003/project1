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
@RequestMapping(value = "/api/v1/portfolio")
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping("{nickname}/review")
    public ResponseEntity<?> writeReview(@PathVariable String nickname,@RequestBody ReviewDto reviewDto){
        return ResponseEntity.ok(reviewService.writeReview(nickname, reviewDto));
    }
    @GetMapping("{nickname}/review/{id}")
    public ResponseEntity<?> selectReviewById(@PathVariable String nickname,@PathVariable Long id){
        return ResponseEntity.ok(reviewService.findById(nickname,id));
    }
    @GetMapping("{nickname}/reviews")
    public ResponseEntity<?> selectReviewsByPortfolioNickname(@RequestParam("page") @Min(0) Integer page, @PathVariable String nickname){
        return ResponseEntity.ok(reviewService.findAllReviewsByPortfolioNickname(page,nickname));
    }
    @GetMapping("{nickname}/reviews/all")
    public ResponseEntity<?> selectAllReviews(@PathVariable String nickname){
        return ResponseEntity.ok(reviewService.findAll(nickname));
    }
}
