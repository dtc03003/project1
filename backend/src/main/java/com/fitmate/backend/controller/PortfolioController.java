package com.fitmate.backend.controller;

import com.fitmate.backend.dto.PortfolioDto;
import com.fitmate.backend.service.PortfolioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="PortfolioController" , description = "포트폴리오 관리")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class PortfolioController {
    private final PortfolioService portfolioService;
    @PostMapping("/portfolio")
    public ResponseEntity<?> makePortfolio(PortfolioDto portfolioDto){
        return ResponseEntity.ok(portfolioService.makePortfolio(portfolioDto));
    }
    @GetMapping("/portfolio/me")
    public ResponseEntity<?> getMyPortfolio(){
        return ResponseEntity.ok(portfolioService.getMyPortfolio());
    }
}
