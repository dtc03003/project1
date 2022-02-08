package com.fitmate.backend.controller;

import com.fitmate.backend.dto.TagDto;
import com.fitmate.backend.entity.Style;
import com.fitmate.backend.service.TagService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "TagController", description = "style의 tag 관련 모든 정보 처리")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class TagController {
    private final TagService tagService;

    @PostMapping("/tag/tagging")
    public ResponseEntity<String> tagging(@RequestBody TagDto tagDto){
        return ResponseEntity.ok(tagService.tagging(tagDto));
    }

    @DeleteMapping("/tag/untagging")
    public ResponseEntity<String> untagging(@RequestBody TagDto tagDto){
        return ResponseEntity.ok(tagService.untagging(tagDto));
    }

    @GetMapping("/tag/{styleId}")
    public ResponseEntity<List<String>> getTagByStyle(@PathVariable Long styleId){
        return ResponseEntity.ok(tagService.getTagByStyle(styleId));
    }
}
