package com.fitmate.backend.controller;

import com.fitmate.backend.dto.StyleDto;
import com.fitmate.backend.entity.Style;
import com.fitmate.backend.service.StyleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="StyleController" , description = "포트폴리오의 스타일 관리")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class StyleController {
    private final StyleService styleService;

    @PostMapping("/style")
    public ResponseEntity<?> postStyle(@RequestBody StyleDto styleDto){
        return ResponseEntity.ok(styleService.postStyle(styleDto));
    }
    @GetMapping("/style/{id}")
    public ResponseEntity<?> getStyle(@PathVariable Long id){
        return ResponseEntity.ok(styleService.getStyle(id));
    }
}
