package com.fitmate.backend.controller;

import com.fitmate.backend.dto.ChatRoomDto;
import com.fitmate.backend.entity.ChatRoom;
import com.fitmate.backend.entity.Message;
import com.fitmate.backend.service.ChatRoomService;
import com.fitmate.backend.service.MessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.RequestEntity;
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
    @GetMapping("/room/{id}")
    public ResponseEntity<?> roomTitle(@PathVariable Long id){
        return ResponseEntity.ok(chatRoomService.getRoomTitle(id));
    }
    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoom>> findAll() {
        return ResponseEntity.ok(chatRoomService.findAllChatRooms());
    }
    @PostMapping("/room")
    public ResponseEntity<ChatRoom> createRoom(@RequestBody ChatRoomDto chatRoomDto){
        return ResponseEntity.ok(chatRoomService.createChatRoom(chatRoomDto));
    }
    @GetMapping("/room/{id}/messages")
    public ResponseEntity<List<Message>> findAllMessagesByRoomId(@PathVariable Long id, @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size= 10) Pageable pageable){
        return ResponseEntity.ok(messageService.findAllByChatRoomId(id, pageable));
    }
//    @GetMapping("/room/")
}
