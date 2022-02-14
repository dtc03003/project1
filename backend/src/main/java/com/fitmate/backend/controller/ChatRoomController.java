package com.fitmate.backend.controller;

import com.fitmate.backend.entity.ChatRoom;
import com.fitmate.backend.entity.Message;
import com.fitmate.backend.service.ChatRoomService;
import com.fitmate.backend.service.MessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ChatRoomController", description = "ChattingRoom 관리")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/chat")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final MessageService messageService;
    @PostMapping("/room")
    public ResponseEntity<ChatRoom> createRoom(){
        return ResponseEntity.ok(chatRoomService.createChatRoom());
    }
    @GetMapping("/room/{nickname}")
    public ResponseEntity<?> findByNickname(@PathVariable String nickname, @RequestParam(required = false) String accessCode){
        return ResponseEntity.ok(chatRoomService.enterRoom(nickname,accessCode));
    }
    @GetMapping("/room/{id}/messages")
    public ResponseEntity<List<Message>> findAllMessagesByRoomId(@PathVariable Long id,
                                                                 @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size= 50) Pageable pageable) {
        return ResponseEntity.ok(messageService.findAllByChatRoomId(id, pageable));
    }
}
