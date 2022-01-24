package com.fitmate.backend.controller;

import com.fitmate.backend.dto.NoticeDto;
import com.fitmate.backend.entity.Notice;
import com.fitmate.backend.service.NoticeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "NoticeController", description = "Notion board 게시글 정보 처리")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping(value = "/notice/create")
    public ResponseEntity<Notice> createNotice(@RequestBody NoticeDto noticeDto){
        return ResponseEntity.ok(noticeService.createNotice(noticeDto));
    }
}
