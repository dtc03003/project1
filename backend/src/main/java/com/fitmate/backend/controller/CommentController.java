package com.fitmate.backend.controller;

import com.fitmate.backend.dto.CommentDto;
import com.fitmate.backend.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CommentController", description = "Qna Board Comment 정보 처리")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {
    private final CommentService commentService;

    @PostMapping(value="/comment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto){
        return ResponseEntity.ok(commentService.createComment(commentDto));
    }

    @GetMapping(value = "/comment/{qnaId}")
    public ResponseEntity<List<CommentDto>> findComment(@PathVariable Long qnaId){
        return ResponseEntity.ok(commentService.findComment(qnaId));
    }

    @PutMapping(value = "/comment")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto){
        return ResponseEntity.ok(commentService.updateComment(commentDto));
    }

    @DeleteMapping(value = "/comment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id){
        return ResponseEntity.ok(commentService.deleteComment(id));
    }
}
