package com.fitmate.backend.service;

import com.fitmate.backend.dto.ChatRoomDto;
import com.fitmate.backend.entity.ChatRoom;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberService memberService;

    public ChatRoom createChatRoom(ChatRoomDto chatRoomDto) {
        Member host = memberService.getMyInfo();
        Member customer = memberService.findMemberByNickname(chatRoomDto.getNickname());
        return chatRoomRepository.save(ChatRoom.builder()
                .host(host)
                .customer(customer)
                .title(host.getNickname() + "고객님과의 방")
                .build());
    }
    public List<ChatRoom> findAllChatRooms(){
        return chatRoomRepository.findAll();
    }

    public String getRoomTitle(Long id){
        return chatRoomRepository.getById(id).getTitle();
    }
    public ChatRoom findById(Long id){
        return chatRoomRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
