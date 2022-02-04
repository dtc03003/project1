package com.fitmate.backend.controller;

import com.fitmate.backend.dto.FollowDto;
import com.fitmate.backend.service.FollowService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "FollowController", description = "stylist 팔로우와 팔로우 취소 팔로우 목록 등 팔로우 관련 모든 정보 처리")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class FollowController {
    private final FollowService followService;

//    public ResponseEntity<FollowDto> follow(){
//
//    }
}
