package com.fitmate.backend.controller;

import com.fitmate.backend.entity.Carousel;
import com.fitmate.backend.service.CarouselService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CarouselController", description = "Carousel 이미지 저장 정보")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class CarouselController {
    private final CarouselService carouselService;

    @PostMapping("/carousel/save")
    public ResponseEntity<String> saveCarousel(@RequestBody String imgSrc){
        return ResponseEntity.ok(carouselService.saveCarouselImg(imgSrc));
    }

    @DeleteMapping("/carousel/delete/{carouselId}")
    public ResponseEntity<String> deleteCarousel(@PathVariable Long carouselId){
        return ResponseEntity.ok(carouselService.deleteCarouselImg(carouselId));
    }

    @GetMapping("/carousel/all")
    public ResponseEntity<List<Carousel>> getAllCarousel(){
        return ResponseEntity.ok(carouselService.getAllCarousel());
    }
}
