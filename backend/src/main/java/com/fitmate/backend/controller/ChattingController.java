package com.fitmate.backend.controller;

import com.fitmate.backend.dto.ChattingDto;
import com.fitmate.backend.entity.Chatting;
import com.fitmate.backend.service.ChattingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ChattingController", description = "Chatting 메시지 관리")
@Controller
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/chatting")
public class ChattingController {
    private final ChattingService chattingService;

    @MessageMapping("/receive")
    @SendTo("/send")
    public Chatting chattingHandler(ChattingDto chattingDto){
        return chattingService.chattingHandler(chattingDto);
    }
}
