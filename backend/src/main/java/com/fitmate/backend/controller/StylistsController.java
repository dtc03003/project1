package com.fitmate.backend.controller;

import com.fitmate.backend.dto.PortfolioDto;
import com.fitmate.backend.entity.Portfolio;
import com.fitmate.backend.entity.Style;
import com.fitmate.backend.service.StylistsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "StylistsController", description = "stylists에 필요한 정보 처리")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class StylistsController {
    private final StylistsService stylistsService;

    @GetMapping(value = "/stylists/sortByFollower")
    public ResponseEntity<List<Portfolio>> sortByFollower(){
        return ResponseEntity.ok(stylistsService.sortByFollower());
    }

    @GetMapping(value = "/stylists/sortByGrade")
    public ResponseEntity<List<Portfolio>> sortByGrade(){
        return ResponseEntity.ok(stylistsService.sortByGrade());
    }

    @GetMapping(value = "/stylists/sortByLatest")
    public ResponseEntity<List<Portfolio>> sortByLatest(){
        return ResponseEntity.ok(stylistsService.sortByLatest());
    }

    @GetMapping(value = "/stylists/latestStylesOfStylist/{stylistNickname}")
    public ResponseEntity<List<Style>> latestStylesOfStylist(@PathVariable String stylistNickname){
        return ResponseEntity.ok(stylistsService.latestStylesOfStylist(stylistNickname));
    }

    @GetMapping(value = "/stylists/search/{stylistNickname}")
    public ResponseEntity<List<Portfolio>> searchByStylistNickname(@PathVariable String stylistNickname){
        return ResponseEntity.ok(stylistsService.searchByStylistNickname(stylistNickname));
    }
}
