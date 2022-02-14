package com.fitmate.backend.controller;

import com.fitmate.backend.dto.MessageDto;
import com.fitmate.backend.entity.Message;
import com.fitmate.backend.service.MessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final SimpMessagingTemplate template;
    @CrossOrigin("*")
    @MessageMapping("/message")
    public void sendMessage(@Payload MessageDto messageDto){
        System.out.println("전달 메시지 : "+ messageDto);
        Message message = messageService.insertMessage(messageDto);
        template.convertAndSend("/sub/" + message.getChatRoom().getId(), message);
    }
}
