package com.fitmate.backend.controller;

import com.fitmate.backend.dto.GradeDto;
import com.fitmate.backend.service.GradeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "RateController", description = "stylist의 평점 관련 정보 처리")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class GradeController {
    private final GradeService gradeService;

    @PutMapping(value="/rate/update")
    public ResponseEntity<GradeDto> updateGrade(@RequestBody GradeDto gradeDto){
        return ResponseEntity.ok(gradeService.calculateGrade(gradeDto));
    }

    @GetMapping(value = "/rate/{stylistNickname}")
    public ResponseEntity<Float> findGradeByStylistNickname(@PathVariable String stylistNickname){
        return ResponseEntity.ok(gradeService.findGradeByStylist(stylistNickname));
    }
}
