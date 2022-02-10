package com.fitmate.backend.controller;

import com.fitmate.backend.dto.StyleCommentDto;
import com.fitmate.backend.service.StyleCommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@Tag(name="StyleCommentController" , description = "포트폴리오의 스타일 댓글 관리")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/portfolio")
public class StyleCommentController {
    private final StyleCommentService styleCommentService;

    @PostMapping("/style/{id}/comment")
    public ResponseEntity<?> writeComment(@PathVariable Long id, @RequestBody StyleCommentDto styleCommentDto){
        return ResponseEntity.ok(styleCommentService.writeComment(id,styleCommentDto));
    }
    @GetMapping("/style/{id}/comments")
    public ResponseEntity<?> selectCommentsByStyleId(@RequestParam("page") @Min(0) Integer page, @PathVariable Long id){
        return ResponseEntity.ok(styleCommentService.findAllCommentsByStyleId(page,id));
    }
    @GetMapping("/style/{id}/comments/all")
    public ResponseEntity<?> selectAllCommentsByStyleId(@PathVariable Long id){
        return ResponseEntity.ok(styleCommentService.getCommentsByStyleId(id));
    }

    @GetMapping("/style/comment/{id}")
    public ResponseEntity<?> selectCommentByCommentId(@PathVariable Long id){
        return ResponseEntity.ok(styleCommentService.getCommentByCommentId(id));
    }
}
