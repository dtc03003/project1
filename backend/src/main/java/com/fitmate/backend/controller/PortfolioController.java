package com.fitmate.backend.controller;

import com.fitmate.backend.dto.PortfolioDto;
import com.fitmate.backend.service.PortfolioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.nio.file.Path;

@Tag(name="PortfolioController" , description = "포트폴리오 관리")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @PostMapping("/portfolio")
    public ResponseEntity<?> makePortfolio(@RequestBody PortfolioDto portfolioDto){
        return ResponseEntity.ok(portfolioService.makePortfolio(portfolioDto));
    }
    @PutMapping("/portfolio/about")
    public ResponseEntity<?> updateMyPortfolioForAbout(@RequestBody PortfolioDto portfolioDto){
        return ResponseEntity.ok(portfolioService.updateAbout(portfolioDto));
    }
    @GetMapping("/portfolio/{nickname}")
    public ResponseEntity<?> getPortfolioByNickname(@PathVariable String nickname){
        return ResponseEntity.ok(portfolioService.getPortfolioByNickname(nickname));
    }
    @GetMapping("/portfolios")
    public ResponseEntity<?> findAllPortfoliosOrderByDesc(@RequestParam("page") @Min(0) Integer page){
        return ResponseEntity.ok(portfolioService.findAllPortfoliosOrderByDesc(page));
    }
    @GetMapping("/portfolios/all")
    public ResponseEntity<?> findAllPortfolios(){
        return ResponseEntity.ok(portfolioService.findAllPortfolios());
    }
    @DeleteMapping("/portfolio")
    public ResponseEntity<?> deleteMyPortfolio(){
        return ResponseEntity.ok(portfolioService.deleteMyPortfolio());
    }
}
