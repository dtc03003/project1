package com.fitmate.backend.controller;

import com.fitmate.backend.dto.NoticeDto;
import com.fitmate.backend.entity.Notice;
import com.fitmate.backend.service.NoticeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "NoticeController", description = "Notion board 게시글 정보 처리")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping(value = "/notice")
    public ResponseEntity<Notice> createNotice(@RequestBody NoticeDto noticeDto){
        return ResponseEntity.ok(noticeService.createNotice(noticeDto));
    }

    @GetMapping(value = "/noticeList")
    public ResponseEntity<List<Notice>> findNotice(){
        return ResponseEntity.ok(noticeService.findNotice());
    }

    @GetMapping(value = "/notice/{id}")
    public ResponseEntity<Notice> findNoticeById(@PathVariable Long id){
        return ResponseEntity.ok(noticeService.findNoticeById(id));
    }

    @DeleteMapping(value = "/notice/{id}")
    public ResponseEntity<String> deleteNoticeById(@PathVariable Long id){
        return ResponseEntity.ok(noticeService.deleteNoticeById(id));
    }

    @PutMapping(value = "/notice")
    public ResponseEntity<Notice> updateNotice(@RequestBody NoticeDto noticeDto){
        return ResponseEntity.ok(noticeService.updateNotice(noticeDto));
    }
}
