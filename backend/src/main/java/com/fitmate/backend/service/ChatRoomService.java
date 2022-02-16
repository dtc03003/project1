package com.fitmate.backend.service;

import com.fitmate.backend.advice.exception.NotFoundChatRoom;
import com.fitmate.backend.entity.ChatRoom;
import com.fitmate.backend.entity.Member;
import com.fitmate.backend.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberService memberService;

    public ChatRoom createChatRoom() {
        Member host = memberService.getMyInfo();
        if(chatRoomRepository.existsByHost(host)){
            System.out.println("이미 존재하는 방이 있습니다!");
            return findByHostNickname(host.getNickname());
        }
        return chatRoomRepository.save(ChatRoom.builder()
                .host(host)
                .title(host.getNickname()+"의 상담실")
                .build());
    }
    public ChatRoom findById(Long id){
        return chatRoomRepository.findById(id).orElseThrow(NotFoundChatRoom::new);
    }
    public ChatRoom enterRoom(String nickname, String accessCode){
        if(!memberService.getMyInfo().getNickname().equals(nickname)){
            Member host = memberService.findMemberByNickname(nickname);
            System.out.println(accessCode);
            return chatRoomRepository.findByHostEqualsAndAccessCode(host, accessCode).orElseThrow(RuntimeException::new);
        }
        return findByHostNickname(nickname);
    }
    public ChatRoom findByHostNickname(String nickname){
        Member host = memberService.findMemberByNickname(nickname);
        return chatRoomRepository.findByHost(host).orElseThrow(NotFoundChatRoom::new);
    }
    public ChatRoom updateAccessCode(Long id){
        ChatRoom chatRoom = chatRoomRepository.findById(id).orElseThrow(NotFoundChatRoom::new);
        chatRoom.updateAccessCode();
        return chatRoomRepository.save(chatRoom);
    }
}
