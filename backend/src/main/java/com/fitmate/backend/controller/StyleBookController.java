package com.fitmate.backend.controller;

import com.fitmate.backend.entity.Style;
import com.fitmate.backend.service.StyleBookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "StyleBookController", description = "StyleBook 관련 정보처리")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class StyleBookController {
    private final StyleBookService styleBookService;

    @GetMapping("/styleBook/search/{tagList}")
    public ResponseEntity<List<Style>> searchByTag(@PathVariable List<String> tagList){
        return ResponseEntity.ok(styleBookService.searchByTag(tagList));
    }

    @GetMapping("/styleBook/search")
    public ResponseEntity<List<Style>> search(){
        return ResponseEntity.ok(styleBookService.searchByTag(new ArrayList<>()));
    }
}
