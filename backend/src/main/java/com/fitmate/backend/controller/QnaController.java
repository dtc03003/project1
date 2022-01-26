package com.fitmate.backend.controller;

import com.fitmate.backend.dto.QnaDto;
import com.fitmate.backend.entity.Qna;
import com.fitmate.backend.service.NoticeService;
import com.fitmate.backend.service.QnaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "QnaController", description = "Qna Board 게시글 정보 처리")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class QnaController {
    private final QnaService qnaService;

    @PostMapping(value = "/qna")
    public ResponseEntity<QnaDto> createNotice(@RequestBody QnaDto qnaDto){
        return ResponseEntity.ok(qnaService.createQna(qnaDto));
    }

    @GetMapping(value = "/qnaListPage")
    public ResponseEntity<List<QnaDto>> findQna(@RequestParam("page") Integer page){
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        return ResponseEntity.ok(qnaService.findQna(pageRequest));
    }

    @GetMapping(value = "/qnaList")
    public ResponseEntity<List<QnaDto>> findAllQna(){
        return ResponseEntity.ok(qnaService.findQna());
    }

    @GetMapping(value="/qna/{id}")
    public ResponseEntity<QnaDto> findQnaById(@PathVariable Long id){
        return ResponseEntity.ok(qnaService.findQnaById(id));
    }

    @PutMapping(value = "/qna")
    public ResponseEntity<QnaDto> updateQna(@RequestBody QnaDto qnaDto){
        return ResponseEntity.ok(qnaService.updateQna(qnaDto));
    }

    @DeleteMapping(value="/qna/{id}")
    public ResponseEntity<String> deleteQnaById(@PathVariable Long id){
        return ResponseEntity.ok(qnaService.deleteQnaById(id));
    }
}
