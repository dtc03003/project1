package com.fitmate.backend.service;

import com.fitmate.backend.dto.MessageDto;
import com.fitmate.backend.entity.ChatRoom;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.entity.Message;
import com.fitmate.backend.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.AuthenticationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final MemberService memberService;
    private final ChatRoomService chatRoomService;
    public Message insertMessage(MessageDto messageDto){
        Member sender = memberService.findMemberById(messageDto.getSender());
        ChatRoom chatRoom = chatRoomService.findById(messageDto.getChatRoomId());
        return messageRepository.save(MessageDto.toEntity(messageDto,chatRoom,sender));
    }
    public List<Message> findAllByChatRoomId(Long id, Pageable pageable){
        return messageRepository.findAllByChatRoomId(id, pageable);
    }
    public void deleteMessagesInRoom(ChatRoom chatRoom){
        messageRepository.deleteAllByChatRoom(chatRoom);
    }
}
