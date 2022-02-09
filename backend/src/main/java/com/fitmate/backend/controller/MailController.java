package com.fitmate.backend.controller;

import com.fitmate.backend.dto.MailDto;
import com.fitmate.backend.service.MailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="MailController" , description = "메일 처리")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/mail")
public class MailController {
    private final MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<?> sendTestMail(@RequestBody MailDto mailDto){
        System.out.println(mailDto);
        return ResponseEntity.ok(mailService.sendMail(mailDto));
    }
}
